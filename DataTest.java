import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataTest {

	public static void main(String[] args) {
/*		Date today = new Date();
		
		SimpleDateFormat dateForm = new SimpleDateFormat("yyyy�� MM�� dd�� hh�� mm�� ss��");
		
		System.out.println(dateForm.format(today));
		
		System.out.println(today);
*/
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR);
		int min = now.get(Calendar.MINUTE);
		
		System.out.println("����ð���"+hour+"��"+min+"�� �Դϴ�.");
		
		}

}
