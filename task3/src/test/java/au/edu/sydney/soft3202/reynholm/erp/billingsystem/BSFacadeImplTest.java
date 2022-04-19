package au.edu.sydney.soft3202.reynholm.erp.billingsystem;

import au.edu.sydney.soft3202.reynholm.erp.client.ClientReporting;
import au.edu.sydney.soft3202.reynholm.erp.compliance.ComplianceReporting;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthToken;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthenticationModule;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthorisationModule;
import au.edu.sydney.soft3202.reynholm.erp.project.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BSFacadeImplTest {
    private BSFacade bsFacade;
    private Project project;
    private AuthToken tokenBasic;
    private AuthToken tokenSecure;
    private AuthorisationModule authorisationModule;
    private AuthenticationModule authenticationModule;
    private ClientReporting clientReporting;
    private ComplianceReporting complianceReporting;

    @BeforeEach
    public void setUp() {
        bsFacade = new BSFacadeImpl();
        project = mock(Project.class);
        tokenBasic = mock(AuthToken.class);
        tokenSecure = mock(AuthToken.class);
        authenticationModule = mock(AuthenticationModule.class);
        authorisationModule = mock(AuthorisationModule.class);
        clientReporting = mock(ClientReporting.class);
        complianceReporting = mock(ComplianceReporting.class);

        when(authenticationModule.login("basic","basic")).thenReturn(tokenBasic);
        when(tokenBasic.getUsername()).thenReturn("basic");
        when(tokenBasic.getToken()).thenReturn("basictoken");
        when(authorisationModule.authorise(tokenBasic, false)).thenReturn(true);
        when(authorisationModule.authorise(tokenBasic, true)).thenReturn(false);

        when(authenticationModule.login("secure","secure")).thenReturn(tokenSecure);
        when(authorisationModule.authorise(tokenSecure, true)).thenReturn(true);
        when(authorisationModule.authorise(tokenSecure, false)).thenReturn(false);
        when(tokenSecure.getUsername()).thenReturn("secure");
        when(tokenSecure.getToken()).thenReturn("securetoken");
    }

    /*******************************************************************
     ******************** NON PERMISSIONS MODULE TEST*******************
     *******************************************************************/
    @Test
    void NonPermissionInjected_AddProject() {
        assertThrows(
                IllegalStateException.class,
                () -> bsFacade.addProject("project", "client", 1.0, 2.0)
        );
    }

    @Test
    void NonPermissionInjected_AddTask() {
        assertThrows(
                IllegalStateException.class,
                () -> bsFacade.addTask(1, "task", 1, false)
        );
    }

    @Test
    void NonPermissionInjected_Audit() {
        assertThrows(
                IllegalStateException.class,
                () -> bsFacade.audit()
        );
    }

    @Test
    void NonPermissionInjected_FinaliseProject() {
        assertThrows(
                IllegalStateException.class,
                () -> bsFacade.finaliseProject(1)
        );
    }

    @Test
    void NonPermissionInjected_FindProjectId() {
        assertThrows(
                IllegalStateException.class,
                () -> bsFacade.findProjectID("project", "client")
        );
    }

    @Test
    void NonPermissionInjected_Login() {
        assertThrows(
                IllegalStateException.class,
                () -> bsFacade.login("basic","basic")
        );
    }

    @Test
    void NonPermissionInjected_Logout() {
        assertThrows(
                IllegalStateException.class,
                () -> bsFacade.logout()
        );
    }


    @Test
    void NonPermissionInjected_RemoveProject() {
        assertThrows(
                IllegalStateException.class,
                () -> bsFacade.removeProject(1)
        );
    }

    @Test
    void NonPermissionInjected_SetProjectCeiling() {
        assertThrows(
                IllegalStateException.class,
                () -> bsFacade.setProjectCeiling(1, 1)
        );
    }


    /*******************************************************************
     *************** ADDPROJECT ILLEGAL ARGUMENT EXCEPTION *************
     *******************************************************************/

    @Test
    void overrate_Requirement_1() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject("project", "client", 1.0, 1.0)
        );
    }

    @Test
    void overrate_Requirement_2() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject("project", "client", 1.0, 0.9)
        );
    }

    @Test
    void overrate_Requirement_3() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject("project", "client", 1.0, 0.01)
        );
    }

    @Test
    void overrate_Requirement_4() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject("project", "client", 1.0, 0.0)
        );
    }

    @Test
    void overrate_Requirement_5() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject("project", "client", 1.0, -1.0)
        );
    }

    @Test
    void overrate_Requirement_6() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject("project", "client", 1.0, 100.0)
        );
    }

    @Test
    void overrate_Requirement_7() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject("project", "client", 1.0, 101.0)
        );
    }

    @Test
    void standardrate_Requirement_1() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject("project", "client", 0.01, 99.0)
        );
    }

    @Test
    void standardrate_Requirement_2() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject("project", "client", 0.0, 99.0)
        );
    }

    @Test
    void standardrate_Requirement_3() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject("project", "client", -1.0, 99.0)
        );
    }

    @Test
    void standardrate_Requirement_4() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject("project", "client", 100.0, 99.0)
        );
    }

    @Test
    void standardrate_Requirement_5() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject("project", "client", 101.0, 99.0)
        );
    }

    @Test
    void name_Requirement_1() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject(null, "client", 1.0, 99.0)
        );
    }

    @Test
    void name_Requirement_2() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject("", "client", 1.0, 99.0)
        );
    }

    @Test
    void client_Requirement_1() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject("project", null, 1.0, 99.0)
        );
    }

    @Test
    void client_Requirement_2() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject("project", "", 1.0, 99.0)
        );
    }

    /*******************************************************************
     ******************** ADDPROJECT STANDARD BEHAVIOR *****************
     *******************************************************************/

    @Test
    void addProject_StandardBehavior() {

    }


    /*******************************************************************
     ***** INAPPROPRIATELY AUTHENTICATED AND AUTHORISED USER SET *******
     *******************************************************************/

    // addProject() requires logged in as a 'basic' authorised user
    @Test
    void InappropriatelyAuthenticatedAndAuthorisedUser_AddProject() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenSecure = authenticationModule.login("secure","secure");
        assertThrows(
                IllegalStateException.class,
                () -> bsFacade.addProject("project", "client", 1.0, 50.0)
        );
    }




    // removeProject() requires logged in as a 'secure' authorised user
    @Test
    void InappropriatelyAuthenticatedAndAuthorisedUser_RemoveProject() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenBasic = authenticationModule.login("basic","basic");
        assertThrows(
                IllegalStateException.class,
                () -> bsFacade.removeProject(1)
        );
    }

    /*******************************************************************
     ************************* EXCEPTION PRIORITY **********************
     *******************************************************************/

    @Test
    void overrate_Requirement() {
        bsFacade.injectAuth(authenticationModule, authorisationModule);
        tokenSecure = authenticationModule.login("secure","secure");
        assertThrows(
                IllegalArgumentException.class,
                () -> bsFacade.addProject("project", "client", 1.0, 1.0)
        );
    }


    /*******************************************************************
     *******************************************************************
     *******************************************************************/
}
