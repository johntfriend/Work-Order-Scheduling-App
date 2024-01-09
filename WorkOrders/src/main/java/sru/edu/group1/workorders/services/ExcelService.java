package sru.edu.group1.workorders.services;

import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sru.edu.group1.workorders.repository.BuildingRepository;
import sru.edu.group1.workorders.repository.DepartmentsRepository;
import sru.edu.group1.workorders.repository.RoomsRepository;
import sru.edu.group1.workorders.domain.Departments;
import sru.edu.group1.workorders.domain.Rooms;

@Service
public class ExcelService {
  @Autowired
  DepartmentsRepository Deprepository;
  @Autowired
  RoomsRepository Roomsrepository;
  @Autowired
  BuildingRepository buildRepository;

/**
 * Checks value of an XSSF cell and turns an int into a string
 * @param testCell
 * @return cell value as string
 */
  public String checkStringType(XSSFCell testCell)
	{
		if(testCell.getCellType() == CellType.NUMERIC)
		{
			return Integer.toString((int)testCell.getNumericCellValue());
		}
		return testCell.getStringCellValue();
	}
  /**
   * Checks value of an XSSF cell and turns an string into an int value
   * @param testCell
   * @return cell value as an int
   */
	public int checkIntType(XSSFCell testCell)
	{
		if(testCell.getCellType().equals(1))
		{
			return Integer.parseInt(testCell.getStringCellValue());
		}
		return (int)testCell.getNumericCellValue();
	}
  
	/**
	 * Generates list of department objects
	 * @return
	 */
  public List<Departments> getAllDepartments() {
	    return Deprepository.findAll();
	  }
/**
 * Generates list of room objects
 * @return
 */
public List<Rooms> getAllRooms() {
    return Roomsrepository.findAll();
  }

}


