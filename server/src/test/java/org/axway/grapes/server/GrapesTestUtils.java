package org.axway.grapes.server;

import com.google.common.collect.Lists;
import org.axway.grapes.server.db.RepositoryHandler;
import org.axway.grapes.server.db.datamodel.DbCredential;
import org.axway.grapes.server.db.datamodel.DbCredential.AvailableRoles;
import org.axway.grapes.server.db.datamodel.DbOrganization;
import org.axway.grapes.server.webapp.auth.GrapesAuthenticator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GrapesTestUtils {

    public final static String ORGANIZATION_NAME_4TEST = "corp";
    public final static String CORPORATE_GROUPID_4TEST = "com.corporate.test";

    public final static String USER_4TEST = "user";
    public final static String PASSWORD_4TEST = "password";

    public final static String WRONG_USER_4TEST = "wrongUser";
    public final static String WRONG_PASSWORD_4TEST = "wrongPassword";

    public static RepositoryHandler getRepoHandlerMock() {
        try{
            final RepositoryHandler repositoryHandler = mock(RepositoryHandler.class);

            final DbCredential user = new DbCredential();
            user.setUser(USER_4TEST);
            user.setPassword(GrapesAuthenticator.encrypt(PASSWORD_4TEST));
            user.addRole(AvailableRoles.ARTIFACT_CHECKER);
            user.addRole(AvailableRoles.DATA_DELETER);
            user.addRole(AvailableRoles.DATA_UPDATER);
            user.addRole(AvailableRoles.DEPENDENCY_NOTIFIER);
            user.addRole(AvailableRoles.LICENSE_CHECKER);
            when(repositoryHandler.getCredential(USER_4TEST)).thenReturn(user);

            final DbCredential wrongUser = new DbCredential();
            wrongUser.setUser(WRONG_USER_4TEST);
            wrongUser.setPassword(GrapesAuthenticator.encrypt(WRONG_PASSWORD_4TEST));
            when(repositoryHandler.getCredential(WRONG_USER_4TEST)).thenReturn(wrongUser);

            final DbOrganization organization = new DbOrganization();
            organization.setName(ORGANIZATION_NAME_4TEST);
            organization.getCorporateGroupIdPrefixes().add(CORPORATE_GROUPID_4TEST);
            when(repositoryHandler.getOrganization(ORGANIZATION_NAME_4TEST)).thenReturn(organization);
            when(repositoryHandler.getAllOrganizations()).thenReturn(Lists.newArrayList(organization));

            return repositoryHandler;

        }catch (Exception e){
            System.err.println("Failed to mock Grapes configuration due to password encryption error.");
        }

        return mock(RepositoryHandler.class);
    }
}
