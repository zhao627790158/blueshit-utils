package cn.blueshit.cn.test.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhaoheng on 17/2/11.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Employee.class)
public class PowerMock {

    @Mock
    private List list;

    @InjectMocks
    private MockService service;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {
        Mockito.when(list.add(Mockito.any())).thenReturn(false);
        System.out.println(service.getList().add(100));
    }

    @Test
    public void testStatic() {
        PowerMockito.mockStatic(Employee.class);
        PowerMockito.when(Employee.getService()).thenReturn(service);
        Mockito.when(list.add(Mockito.any())).thenReturn(true);
        System.out.println(service.getList().add(100));
    }


    @Test
    public void shouldReturnTheCountOfEmployeesUsingTheDomainClass() {

        PowerMockito.mockStatic(Employee.class);
        PowerMockito.when(Employee.count()).thenReturn(900);

        EmployeeService employeeService = new EmployeeService();
        assertEquals(900, employeeService.getEmployeeCount());

    }

    @Test
    public void shouldReturnProjectedCountOfEmployeesFromTheService() {

        EmployeeService mock = PowerMockito.mock(EmployeeService.class);
        PowerMockito.when(mock.getEmployeeCount()).thenReturn(8);
        EmployeeController employeeController = new EmployeeController(mock);
        assertEquals(10, employeeController.getProjectedEmployeeCount());
    }

    @Test
    public void
    shouldInvokeSaveEmployeeOnTheServiceWhileSavingTheEmployee() {

        EmployeeService mock = PowerMockito.mock(EmployeeService.class);
        EmployeeController employeeController = new EmployeeController(mock);
        Employee employee = new Employee();
        employeeController.saveEmployee(employee);
        Mockito.verify(mock).saveEmployee(employee);
    }
}
