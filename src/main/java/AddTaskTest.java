import com.metal.ConsoleApplication;
import com.metal.model.Task;
import com.metal.service.ConsoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ConsoleApplication.class)
public class AddTaskTest {

    @Autowired
    private ConsoleService consoleService;

    @Test
    public void main( ) {


        Task task = new Task();

        String tem_str = "赵丽颖;"+
                "胡歌;"+
                "56度乐队;"+
                "3+X乐队;"+
                "1983组合;"+
                "安又琪;"+
                "安琥;"+
                "爱戴;"+
                "爱乐团;"+
                "艾敬;"+
                "艾尔肯;"+
                "艾梦萌;"+
                "阿朵;"+
                "阿穆隆;"+
                "阿斯茹;"+
                "阿修罗乐队;"+
                "阿丘;"+
                "阿兰;"+
                "阿里郎;"+
                "AK-47乐队;"+
                "A-OK乐队;"+
                "布衣乐队;"+
                "波澜童话乐队;"+
                "病蛹乐队;"+
                "边原;"+
                "贝维;"+
                "北京男孩乐队;"+
                "鲍国安;"+
                "鲍蕾;"+
                "宝晓峰;"+
                "保剑锋;"+
                "冰淇淋格子;"+
                "冰清玉洁;"+
                "毕福剑;"+
                "毕铭鑫;"+
                "便利商店乐队;"+
                "柏寒;"+
                "崔奕;"+
                "崔健;"+
                "崔志刚;"+
                "崔哲铭;"+
                "崔永元;"+
                "崔林;"+
                "春晓;"+
                "春秋乐队;"+
                "池华琼;"+
                "车永莉;"+
                "车晓;"+
                "超级市场乐队;"+
                "超载乐队;"+
                "常宽;"+
                "常宝华;"+
                "常香玉;"+
                "董洁;"+
                "董嘉耀;"+
                "董浩;"+
                "董平;"+
                "董倩;"+
                "董艺;"+
                "董贞;"+
                "董勇;"+
                "董文华;"+
                "董璇;"+
                "董卿;"+
                "东来东往;"+
                "丁薇;"+
                "丁建华;"+
                "丁海峰;"+
                "丁当;"+
                "恩炀;"+
                "二手玫瑰乐队;"+
                "耳光乐队;"+
                "厄刃乐队;"+
                "E欣欣;"+
                "富大龙;"+
                "付静;"+
                "付笛声;"+
                "付辛博;"+
                "傅晶;"+
                "傅海静;"+
                "傅庚辰;"+
                "傅聪;"+
                "傅冲;"+
                "傅彪;"+
                "傅艺伟;"+
                "傅淼;"+
                "浮克;"+
                "房斌;"+
                "方静;"+
                "方圆;"+
                "关智斌;"+
                "关牧村;"+
                "关悦;"+
                "贡米;"+
                "巩贺;"+
                "巩汉林;"+
                "巩新亮;"+
                "巩俐;"+
                "宫正楠;"+
                "宫哲;"+
                "宫雪花;"+
                "龚洁;"+
                "龚蓓苾;"+
                "耿直;"+
                "耿宁;"+
                "耿乐;"+
                "华少翌;"+
                "花儿乐队;"+
                "黄奕;"+
                "黄觉;"+
                "黄建新;"+
                "黄健中;"+
                "黄健翔;"+
                "黄华丽;"+
                "黄海波;"+
                "黄海冰;"+
                "黄宏;"+
                "黄格选;"+
                "黄渤;"+
                "黄志忠;"+
                "黄仲昆;"+
                "黄鑫;"+
                "镜花缘乐队;"+
                "敬一丹;"+
                "景岗山;"+
                "景甜;"+
                "井柏然;"+
                "井莉;"+
                "经蓓;"+
                "君君;"+
                "居文沛;"+
                "鞠萍;"+
                "焦晃;"+
                "蒋大为;"+
                "蒋雯丽;"+
                "蒋雯;"+
                "蒋欣;"+
                "蒋勤勤;"+
                "孔维;"+
                "孔镱姗;"+
                "孔镱珊;"+
                "孔祥东;"+
                "康辉;"+
                "康洪雷;"+
                "咖啡因乐队;"+
                "寇振海;"+
                "空中狂欢节乐队;"+
                "柯汶;"+
                "K362乐队;"+
                "大春子;"+
                "栾剑;"+
                "厉娜;"+
                "罗京;"+
                "罗杰;"+
                "罗海琼;"+
                "罗钢;"+
                "罗方序;"+
                "罗中旭;"+
                "罗晓芳;"+
                "罗湘晋;"+
                "罗天婵;"+
                "罗珊珊;"+
                "罗琦;"+
                "柳云龙;"+
                "柳岩;"+
                "木马乐队;"+
                "慕容晓晓;"+
                "慕林杉;"+
                "莫沉;"+
                "莫万丹;"+
                "莫小奇;"+
                "莫龙丹;"+
                "末裔乐队;"+
                "末小皮;"+
                "摩天楼乐队;"+
                "冥界乐团;"+
                "小马可;"+
                "明骏女孩;"+
                "明丽;"+
                "眉佳;"+
                "梅婷;"+
                "倪景阳;"+
                "倪虹洁;"+
                "倪大红;"+
                "倪萍;"+
                "倪齐民;"+
                "倪睿思;"+
                "宁静;"+
                "宁浩;"+
                "宁财神;"+
                "宁才;"+
                "宁小娟;"+
                "宁辛;"+
                "脑浊乐队;"+
                "聂建华;"+
                "聂远;"+
                "南合文斗;"+
                "呕吐乐队;"+
                "欧阳奋强;"+
                "欧阳夏丹;"+
                "飘乐队;"+
                "匹诺曹乐队;"+
                "破碎乐队;"+
                "朴树;"+
                "蒲巴甲;"+
                "濮存昕;"+
                "泡泡糖乐队;"+
                "庞龙;"+
                "彭久洋;"+
                "彭宇;"+
                "彭雪;"+
                "彭坦;"+
                "彭心宜;"+
                "彭心怡;"+
                "彭新智;"+
                "彭丽媛;"+
                "瞿颖;"+
                "奇迹乐队;"+
                "奇亮;"+
                "戚薇;"+
                "戚迹;"+
                "祁莺;"+
                "祁艳;"+
                "齐欢;"+
                "齐芳;"+
                "齐中旸;"+
                "齐千郡;"+
                "漆亚灵;"+
                "Q-Ki乐团;"+
                "覃宇;"+
                "舜文齐;"+
                "秦海璐;"+
                "REBORN组合;"+
                "冉宗瑜;"+
                "饶天亮;"+
                "锐乐团;"+
                "任静;"+
                "任光;"+
                "任程伟;"+
                "任志宏;"+
                "任泉;"+
                "任轶男;"+
                "任鲁豫;"+
                "瑞王坟;"+
                "茹萍;"+
                "肉树乐队;"+
                "单田芳;"+
                "单良;"+
                "Super Junior-M;"+
                "SUBS乐队;"+
                "SKO乐队;"+
                "索妮;"+
                "萨顶顶;"+
                "萨日娜;"+
                "山野;"+
                "山人乐队;"+
                "珊子;"+
                "斯巴达人乐队;"+
                "斯琴高娃;"+
                "斯琴格日乐;"+
                "史可;"+
                "史兰芽;"+
                "佟大为;"+
                "佟铁鑫;"+
                "TOOKOO乐队;"+
                "THE VERSE乐队;"+
                "涂经纬;"+
                "谈芳兵;"+
                "谈莉娜;"+
                "谭晶;"+
                "谭杰希;"+
                "谭盾;"+
                "谭维维;"+
                "谭歆柔;"+
                "痛苦的信仰;"+
                "童话演唱团;"+
                "童瑶;"+
                "童蕾;"+
                "UDF地下DJ工厂;"+
                "Water乐队;"+
                "邬倩倩;"+
                "无级生;"+
                "巫刚;"+
                "巫迪文;"+
                "魏佳庆;"+
                "魏积安;"+
                "魏晨;"+
                "魏斌;"+
                "魏晓南;"+
                "魏松;"+
                "魏敏芝;"+
                "韦唯;"+
                "文静;"+
                "武艺;"+
                "文筱芮;"+
                "奚辰华;"+
                "奚美娟;"+
                "解晓东;"+
                "解小东;"+
                "春妮;"+
                "馨子;"+
                "XXX乐队;"+
                "雪村;"+
                "薛佳凝;"+
                "薛之谦;"+
                "胥午梅;"+
                "胥力文;"+
                "希亚;"+
                "夏凡;"+
                "夏雨;"+
                "夏炎;"+
                "宥鸣;"+
                "鄢颇;"+
                "喻恩泰;"+
                "易慧;"+
                "易中天;"+
                "易小星;"+
                "液氧罐头;"+
                "夜叉乐队;"+
                "叶蓓;"+
                "叶一茜;"+
                "叶迎春;"+
                "原华;"+
                "原·味;"+
                "袁成杰;"+
                "袁文栋;"+
                "袁泉;"+
                "邹俊百;"+
                "邹爽;"+
                "邹琛玮;"+
                "卓玛措;"+
                "祝新运;"+
                "宗峰岩;"+
                "子墨;"+
                "子曰乐队;"+
                "子义;"+
                "左右乐队;"+
                "自游乐队;"+
                "自由画像;"+
                "左翎;"+
                "左小青;"+
                "尊龙;"+
                "江一";

        for (String s:tem_str.split(";")){
            Task task1 = new Task();
            task1.setKey_word(s);
            consoleService.createTask(task1);
        }

    }


}