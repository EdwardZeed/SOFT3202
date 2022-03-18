package au.edu.sydney.soft3202.reynholm.erp.billingsystem;

import au.edu.sydney.soft3202.reynholm.erp.project.Project;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthenticationModule;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthorisationModule;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthToken;
import au.edu.sydney.soft3202.reynholm.erp.billingsystem.BSFacade;
import au.edu.sydney.soft3202.reynholm.erp.client.ClientReporting;
import au.edu.sydney.soft3202.reynholm.erp.billingsystem.BSFacadeImpl;
import au.edu.sydney.soft3202.reynholm.erp.compliance.ComplianceReporting;
import au.edu.sydney.soft3202.reynholm.erp.cheatmodule.ERPCheatFactory;


import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import java.util.List;

import com.sun.source.doctree.AuthorTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class BSFacadeImplTest {
    private BSFacadeImpl bsFacadeImpl;
    private AuthenticationModule authenticationModule;
    private AuthorisationModule authorisationModule;
    private AuthToken secure;
    private AuthToken basic;

    @BeforeEach
    public void setUp() {
        authenticationModule = mock(AuthenticationModule.class);
        authorisationModule = mock(AuthorisationModule.class);

        secure = mock(AuthToken.class);
        basic = mock(AuthToken.class);

        when(authenticationModule.login("secure", "secure")).thenReturn(secure);
        when(authenticationModule.login("basic", "basic")).thenReturn(basic);

        when(authorisationModule.authorise(secure, true)).thenReturn(true);
        when(authorisationModule.authorise(basic, false)).thenReturn(true);

        bsFacadeImpl = new BSFacadeImpl();
    }

    @Test
    public void testAddProject() {
        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        bsFacadeImpl.login("basic", "basic");
        Project project = bsFacadeImpl.addProject("testingAssignment", "client1", 50.0, 55.0);
//        assertEquals("testingAssignment", project.getName());
//        assertEquals(50, project.getStandardRate());
//        assertEquals(20, project.getOverDifference());
        assertEquals(1, bsFacadeImpl.getAllProjects().size());
        assertEquals(project, bsFacadeImpl.getAllProjects().get(0));

        Project result = mock(Project.class);
        try(MockedStatic<Project> mockedProject = mockStatic(Project.class)){
            mockedProject.when(Project.makeProject(project.getId(), "testingAssignment", 50.0, 5.0)).thenReturn(result);
            assertThat(project, equalTo(result));
        }
    }

    @Test
    public void testAddProjectFailed(){
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.addProject("testingAssignment", "client1", 50, 55));

        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        bsFacadeImpl.login("basic", "basic");
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.addProject(null, "client1", 50.0, 55.0));
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.addProject("", "client1", 50.0, 55.0));
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.addProject("testingAssignment", "", 50.0, 55.0));
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.addProject("testingAssignment", null, 50.0, 55.0));
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.addProject("testingAssignment", "client1", 101.0, 55.0));
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.addProject("testingAssignment", "client1", 50.0, 10.0));
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.addProject("testingAssignment", "client1", 50.0, 101.0));

        bsFacadeImpl.logout();
        bsFacadeImpl.login("secure", "secure");
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.addProject("testingAssignment", "client1", 50, 55));
    }

    @Test
    public void testRemoveProject() {
        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        bsFacadeImpl.login("secure", "secure");
        Project project = bsFacadeImpl.addProject("testingAssignment", "client1", 50.0, 20.0);
        bsFacadeImpl.removeProject(project.getId());
        assertEquals(0, bsFacadeImpl.getAllProjects().size());
    }

    @Test
    public void testRemoveProjectFailed(){
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.removeProject(1));

        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        bsFacadeImpl.login("basic", "basic");
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.removeProject(1));

        bsFacadeImpl.logout();
        bsFacadeImpl.login("secure", "secure");
        Project project = bsFacadeImpl.addProject("testingAssignment", "client1", 50.0, 20.0);
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.removeProject(project.getId()+1));
    }

    @Test
    public void testAddTask(){
        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        bsFacadeImpl.login("basic", "basic");
        Project project = bsFacadeImpl.addProject("testingAssignment", "client1", 50.0, 20.0);

        boolean result1 = bsFacadeImpl.addTask(project.getId(), "task1", 10, false);
        assertTrue(result1);

        boolean result2 = bsFacadeImpl.addTask(project.getId(), "task2", 60, false);
        assertFalse(result2);

        boolean result3 = bsFacadeImpl.addTask(project.getId(), "task3", 60, true);
        assertTrue(result3);
    }

   @Test
   public void testSetProjectCeiling(){
       bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
       bsFacadeImpl.login("basic", "basic");
       Project project = bsFacadeImpl.addProject("testingAssignment", "client1", 50, 55);
       bsFacadeImpl.setProjectCeiling(project.getId(), 150);

   }

    @Test
    public void testSetProjectCeilingFailed(){
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.setProjectCeiling(1, 150));

        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        bsFacadeImpl.login("basic", "basic");
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.setProjectCeiling(1, 150));

        bsFacadeImpl.logout();
        bsFacadeImpl.login("secure", "secure");
        Project project = bsFacadeImpl.addProject("testingAssignment", "client1", 50.0, 55.0);
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.setProjectCeiling(project.getId(), 0));
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.setProjectCeiling(project.getId(), 1001));
    }

    @Test
    public void testFindProjectID(){
        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        bsFacadeImpl.login("basic", "basic");
        Project project1 = bsFacadeImpl.addProject("testingAssignment1", "client1", 50.0, 55.0);
        Project project2 = bsFacadeImpl.addProject("testingAssignment2", "client2", 60.0, 66.0);

        int ID = bsFacadeImpl.findProjectID("testingAssignment1", "client1");
        assertEquals(project1.getId(), ID);
    }

    @Test
    public void testFindProjectIDFailed(){
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.findProjectID(null, "client1"));
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.findProjectID("testingAssignment1", null));

        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        bsFacadeImpl.login("basic", "basic");
        Project project1 = bsFacadeImpl.addProject("testingAssignment1", "client1", 50.0, 55.0);
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.findProjectID("testingAssignment", "client1"));
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.findProjectID("testingAssignment1", "client"));
    }

    @Test
    public void testSearchProjects(){
        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        bsFacadeImpl.login("basic", "basic");
        Project project1 = bsFacadeImpl.addProject("testingAssignment1", "client1", 50.0, 55.0);
        Project project2 = bsFacadeImpl.addProject("testingAssignment2", "client1", 60.0, 66.0);

        List<Project> projects = bsFacadeImpl.searchProjects("client1");
        assertEquals(2, projects.size());
        Project result1 = mock(Project.class);
        Project result2 = mock(Project.class);
        try(MockedStatic<Project> mockedProject = mockStatic(Project.class)){
            mockedProject.when(() -> Project.makeProject(project1.getId(), "testingAssignment1", 50.0, 5.0)).thenReturn(result1);
            mockedProject.when(() -> Project.makeProject(project2.getId(), "testingAssignment2", 60.0, 6.0)).thenReturn(result2);

            assertThat(result1, equals(projects.get(0)));
            assertThat(result2, equals(projects.get(1)));
        }
    }

    @Test
    public void testSearchProjectsFailed() {
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.searchProjects(null));
    }

    @Test
    public void testAudit() {
        ComplianceReporting complianceReporting = mock(ComplianceReporting.class);
        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        bsFacadeImpl.injectCompliance(complianceReporting);

        bsFacadeImpl.login("secure", "secure");
        Project project = bsFacadeImpl.addProject("testingAssignment", "client1", 50.0, 55.0);
        bsFacadeImpl.audit();

    }

    @Test
    public void testAuditFailed(){
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.audit());

        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        bsFacadeImpl.login("basic", "basic");
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.audit());

    }

    @Test
    public void testFinaliseProjectFailed(){
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.finaliseProject(1));

        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        bsFacadeImpl.login("basic", "basic");
        Project project = bsFacadeImpl.addProject("testingAssignment", "client1", 50.0, 55.0);
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.finaliseProject(project.getId()) );

        bsFacadeImpl.login("secure", "secure");
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.finaliseProject(project.getId()) );

        ClientReporting clientReporting = mock(ClientReporting.class);
        bsFacadeImpl.injectClient(clientReporting);
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.finaliseProject(project.getId()+1) );


    }

    @Test
    public void testInjectAuthFailed(){
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.injectAuth(null, authorisationModule));
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.injectAuth(authenticationModule, null));

    }

    @Test
    public void testLogin(){
        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        boolean login1 = bsFacadeImpl.login("Basic", "basic");
        assertFalse(login1);

        boolean login2 = bsFacadeImpl.login("basic", "secure");
        assertFalse(login2);

        boolean login3 = bsFacadeImpl.login("basic", "basic");
        assertTrue(login3);

    }

    @Test
    public void testLoginFailed(){
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.login("secure", "secure"));

        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.login(null, "secure"));
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.login("secure", null));
        assertThrows(IllegalArgumentException.class, () -> bsFacadeImpl.login(null, null));
    }

    @Test
    public void testLogoutFailed(){
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.logout());

        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        assertThrows(IllegalStateException.class, () -> bsFacadeImpl.logout());
    }
}