import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataTest {

	public static void main(String[] args) {
/*		Date today = new Date();
		
		SimpleDateFormat dateForm = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초");
		
		System.out.println(dateForm.format(today));
		
		System.out.println(today);
*/
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR);
		int min = now.get(Calendar.MINUTE);
		
		System.out.println("현재시간은"+hour+"시"+min+"분 입니다.");
		
		}

}
