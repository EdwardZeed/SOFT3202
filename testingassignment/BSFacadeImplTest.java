package au.edu.sydney.soft3202.testingassignment;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BSFacadeImplTest {
    @BeforeEach
    public void setUp() {
        AuthenticationModule authenticationModule = mock(AuthenticationModule.class);
        AuthorisationModule authorisationModule = mock(AuthorisationModule.class);

        AuthToken secure = mock(AuthToken.class);
        AuthToken basic = mock(AuthToken.class);

        when(authenticationModule.login("secure", "secure")).thenReturn(secure);
        when(authenticationModule.login("basic", "basic")).thenReturn(basic);

        when(authorisationModule.authorise(secure, true)).thenReturn(true);
        when(authorisationModule.authorise(basic, false)).thenReturn(true);

        BSFacadeImpl bsFacadeImpl = new BSFacadeImpl();
    }

    @Test
    public void testAddProject() {
        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        bsFacadeImpl.login("basic", "basic");
        Project project = bsFacadeImpl.addProject("testingAssignment", "client1", 50, 20);
        assertEquals("testingAssignment", project.getName());
        assertEquals(50, project.getStandardRate());
        assertEquals(20, project.getOverDifference());
        assertEquals(1, bsFacadeImpl.getAllProjects().size());
        assertEquals(project, bsFacadeImpl.getAllProjects().get(0));
    }

    @Test
    public void testRemoveProject() {
        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        bsFacadeImpl.login("secure", "secure");
        Project project = bsFacadeImpl.addProject("testingAssignment", "client1", 50, 20);
        bsFacadeImpl.removeProject(project.getId());
        assertEquals(0, bsFacadeImpl.getAllProjects().size());
    }

    @Test
    public void testAddTask(){
        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        bsFacadeImpl.login("basic", "basic");
        Project project = bsFacadeImpl.addProject("testingAssignment", "client1", 50, 20);

        boolean result1 = bsFacadeImpl.addTask(project.getId(), "task1", 10, false);
        assertTrue(result1);

        boolean result2 = bsFacadeImpl.addTask(project.getId(), "task2", 60, false);
        assertFalse(result2);

        boolean result3 = bsFacadeImpl.addTask(project.getId(), "task3", 60, true);
        assertTrue(result3);
    }

    @Test
    public void testFindProjectID(){
        bsFacadeImpl.injectAuth(authenticationModule, authorisationModule);
        bsFacadeImpl.login("basic", "basic");
        Project project1 = bsFacadeImpl.addProject("testingAssignment1", "client1", 50, 20);
        Project project2 = bsFacadeImpl.addProject("testingAssignment2", "client2", 60, 20);

        int ID = bsFacadeImpl.findProjectID("testingAssignment1", "client1");
        assertEquals(project1.getId(), ID);
    }
}
