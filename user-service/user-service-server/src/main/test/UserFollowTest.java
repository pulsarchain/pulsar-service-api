import com.bosha.user.api.dto.UserFollowListDto;
import com.bosha.user.api.service.UserFollowService;
import com.bosha.user.api.service.UserService;
import com.bosha.user.server.UserServiceBoot;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@SpringBootTest(classes = UserServiceBoot.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//public class UserFollowTest {
//    @Autowired
//    private UserFollowService userFollowService;
//
//    @Test
//    public void addOrDeleteFollow() {
//    }
//
//    @Test
//    public void selectUserFollow() {
//
//    }
//
//    @Test
//    public void selectCountByAddress() {
//
//    }
//}

