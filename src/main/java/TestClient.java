import dileepa.com._2015._05.*;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.spi.Provider;
import javax.xml.ws.spi.ServiceDelegate;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * USER : Dileepa
 * DATE : 5/24/15
 * TIME : 9:03 PM
 */
public class TestClient
{

    public static final  String USER_MANAGER_SERVICE =  "http://localhost:8080/UserManagementService/UserManagementService?wsdl";
    public static final  String USER_MANAGER_NAME_SPACE =  "http://dileepa/com/2015/05/";
    public static final  String USER_MANAGER_SOAP11_PORT =  "UserManagementServicePort";

    public static void main(String[] args) {

        URL urlWsdl = null;
        try
        {
            urlWsdl = new URL( USER_MANAGER_SERVICE );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }


        QName serviceQName = new QName( USER_MANAGER_NAME_SPACE, "UserManagerServiceImplService" );
        ServiceDelegate delegate = Provider.provider().createServiceDelegate( urlWsdl, serviceQName, UserManagementService.class );
        QName portQName = new QName( USER_MANAGER_NAME_SPACE, USER_MANAGER_SOAP11_PORT );
        UserManagementService remoteUserStoreManagerService = delegate.getPort( portQName, UserManagementService.class );

        GetAllUsersResponse getAllUsersResponse = remoteUserStoreManagerService.getAllUsers(new GetAllUsersRequest());
        List<User> userList = getAllUsersResponse.getUserList();
        System.out.println("List All Users From Service");
        for ( User user : userList)
        {
            System.out.println(user.getUsername() +"--"+user.getStatus());
        }

        UserSearchCriteria userSearchCriteria = new UserSearchCriteria();
        userSearchCriteria.setCity("mathugama");
        userSearchCriteria.setStatus(UserStatus.BLOCKED.toString());
        SearchUsersRequest searchUsersRequest = new SearchUsersRequest();
        searchUsersRequest.setUserSearchCriteria(userSearchCriteria);

        SearchUsersResponse searchUsersResponse = remoteUserStoreManagerService.searchUsers(searchUsersRequest);
        List<User> searchUserResult = searchUsersResponse.getUserList();
        System.out.println("Criteria --> "+userSearchCriteria.getStatus()+" "+userSearchCriteria.getCity());
        for ( User user : searchUserResult)
        {
            System.out.println(user.getUsername() +"--"+user.getStatus());
        }

    }
}
