package initialize_db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.carousel.model.CarouselDAO_interface;
import com.carousel.model.CarouselJDBCDAO;
import com.carousel.model.CarouselVO;
import com.menu.model.MenuDAOJDBC;
import com.menu.model.MenuDAO_interface;
import com.menu.model.MenuVO;
import com.resinfo.model.ResInfoDAO_interface;
import com.resinfo.model.ResInfoJDBCDAO;
import com.resinfo.model.ResInfoService;
import com.resinfo.model.ResInfoVO;

//跑完sql後再來執行
public class InsertImageDB {
	
	public static void main(String[] args) throws IOException {
		
		/*餐廳基本資訊*/
		
		ResInfoDAO_interface resInfoDAO = new ResInfoJDBCDAO();
		List<ResInfoVO> listOfResInfo =  resInfoDAO.getAll();
		
		
		
		
		for(int i=0; i <listOfResInfo.size();i++) {
			String tableName = "resinfo";
			String filePath = "C:\\imagesDB//"+tableName;
			File file = new File(filePath);
			String[] listName = file.list();
			String imagePath = filePath+"/"+listName[i];
			FileInputStream input = new FileInputStream(imagePath);
			byte[] b = new byte[input.available()];
			input.read(b);
			ResInfoVO resInfoVO = listOfResInfo.get(i);
			resInfoVO.setResimg(b);//
			resInfoDAO.update(resInfoVO);	
		}
		
		
		/*菜單*/
		
		MenuDAO_interface menuDAO = new MenuDAOJDBC();
		List<MenuVO> menuvoList = menuDAO.getAll();
	
		
		
		for(int i=0; i < menuvoList.size();i++) {
			String tableName = "menu";
			String filePath = "C:\\imagesDB//"+tableName;
			File file = new File(filePath);
			String[] listName = file.list();
			
			String imagePath = filePath+"/"+listName[i];
			System.out.println(imagePath);
			FileInputStream input = new FileInputStream(imagePath);
			byte[] b = new byte[input.available()];
			input.read(b);
			MenuVO menuvo = menuvoList.get(i);
			menuvo.setMealimg(b);//
			menuDAO.update(menuvo);	
		}
		
		
		CarouselDAO_interface CarouselDAO = new CarouselJDBCDAO();
		List<CarouselVO> CarouselVOList =  CarouselDAO.getAll();
		
		for(int i=0; i < CarouselVOList.size();i++) {
			String tableName = "carousel";
			String filePath = "C:\\imagesDB//"+tableName;
			File file = new File(filePath);
			String[] listName = file.list();
			
			String imagePath = filePath+"/"+listName[i];
			System.out.println(imagePath);
			FileInputStream input = new FileInputStream(imagePath);
			byte[] b = new byte[input.available()];
			input.read(b);
			CarouselVO carouselVO = CarouselVOList.get(i);
			carouselVO.setCarouselpic(b);
			
			CarouselDAO.update(carouselVO);	
		}
		
		
		
		System.out.println("照片新增完成");

		
		
	}
	
	
	
}
