/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package testsuite;

import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Field;
import java.util.*;

import org.junit.After;
import org.junit.Before;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class CustomerTest {

    private Customer customer; // Declaring customer as a field
    private Set<String> preferences;
    private List<String> advertisements;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void initialize(){
        preferences = new HashSet<>(Arrays.asList("Sports", "Technology"));
        advertisements = new ArrayList<>();
        int maxAdvs = 3;
        customer = new Customer(preferences, advertisements, maxAdvs);
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
    System.setOut(originalOut);
    customer.getAdvertisements().clear(); // Clear advertisements list
    }


@Test // Test for task 1
    public void testCustomerFieldsTask1() {
        Set<String> expectedFields = new HashSet<>(Arrays.asList("preferences", "advertisements", "maxAdvs"));
        Set<String> actualFields = getClassFields(Customer.class);
        assertEquals(expectedFields, actualFields);
    }

@Test //Test for task 2
    public void testCustomerConstructorTask2(){
        assertEquals(preferences, customer.getPreferences());
        assertEquals(advertisements, customer.getAdvertisements());
    }

    // Helper method to get the names of fields in a class
    private Set<String> getClassFields(Class<?> clazz) {
        Set<String> fieldNames = new HashSet<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

//Test for task 3
    @Test
    public void testAddAdvertisementWhenListIsFull() {
        customer.addAdvertisement("ad1");
        customer.addAdvertisement("ad2");
        customer.addAdvertisement("ad3");
        assertFalse(customer.addAdvertisement("ad4"));
        assertEquals(3, customer.getAdvertisements().size());
    }

    @Test
    public void testAddAdvertisementWhenListIsNotFull() {
        customer.addAdvertisement("ad1");
        assertTrue(customer.addAdvertisement("ad2"));
        assertEquals(2, customer.getAdvertisements().size());
    }

    @Test
    public void testAddAdvertisementWithEmptyList() {
        assertTrue(customer.addAdvertisement("ad1"));
        assertEquals(1, customer.getAdvertisements().size());
    }

// Tests for task 4
    //Helper Method to capture sout for assertions
private void captureOutput(Runnable task) throws IOException {
    ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
    PrintStream origOut = System.out;
    System.setOut(new PrintStream(tempOut));
    task.run();
    System.setOut(origOut);
    outContent.reset();

    String output = new String(tempOut.toByteArray()).replaceAll("(\r\n|[\n\r])", "\n");
  outContent.write(output.getBytes());
}
@Test
public void testReadAdvertisementsWhenNIsGreaterThanSize() throws IOException {
  customer.addAdvertisement("ad1");
  customer.addAdvertisement("ad2");
  customer.addAdvertisement("ad3");
  String expectedOutput = "ad3\nad2\nad1\n";
  captureOutput(() -> customer.readAdvertisements(10));
  assertEquals(expectedOutput, outContent.toString());
}


@Test
public void testReadAdvertisementsWhenNIsEqualToSize() throws IOException {
    customer.addAdvertisement("ad1");
    customer.addAdvertisement("ad2");
    customer.addAdvertisement("ad3");
    String expectedOutput = "ad3\nad2\nad1\n";
    captureOutput(() -> customer.readAdvertisements(3));
    assertEquals(expectedOutput, outContent.toString());
    assertEquals(0, customer.getAdvertisements().size());
}

@Test
public void testReadAdvertisementsWhenNIsLessThanSize() throws IOException {
  preferences = new HashSet<>(Arrays.asList("Sports", "Technology"));
  advertisements = new ArrayList<>(Arrays.asList("ad1", "ad2", "ad3", "ad4", "ad5"));
  int maxAdvs = 3;
  customer = new Customer(preferences, advertisements, maxAdvs);
  
  // Verify list size before modification
  assertEquals(5, customer.getAdvertisements().size());
  
  String expectedOutput = "ad5\nad4\nad3\n";
  captureOutput(() -> customer.readAdvertisements(3));
  assertEquals(expectedOutput, outContent.toString());
  
  // Verify list content after modification (remaining elements)
  List<String> expectedList = Arrays.asList("ad1", "ad2");
  assertEquals(expectedList, customer.getAdvertisements());
}

//Test for task 5
@Test
public void testAdvertisingPlatformConstructor() {
    Set<String> forbiddenWords = new HashSet<>(Arrays.asList("badword", "spam"));
    AdvertisingPlatform platform = new AdvertisingPlatform(forbiddenWords);

    assertTrue(platform.getSubscribers().isEmpty()); // Ensure subscribers list starts empty
    assertEquals(forbiddenWords, platform.getForbiddenWords());
}

//Test for task 6
@Test
public void testAddCustomerSuccess() {
    AdvertisingPlatform platform = new AdvertisingPlatform(new HashSet<>()); 
    Customer customer = new Customer(new HashSet<>(), new ArrayList<>(), 3);

    platform.addCustomer(customer);

    assertTrue(platform.getSubscribers().contains(customer));
    assertEquals("customer is added!", outContent.toString().trim()); 
}


@Test
public void testAddCustomerAlreadyExists() {
    AdvertisingPlatform platform = new AdvertisingPlatform(new HashSet<>());
    Customer customer = new Customer(new HashSet<>(), new ArrayList<>(), 3);
    platform.addCustomer(customer);
    outContent.reset(); 

    platform.addCustomer(customer);

    // Use Collections.frequency() to count occurrences
    int occurrences = Collections.frequency(platform.getSubscribers(), customer); 

    assertTrue(occurrences == 1); // Should only be in the list once
    assertEquals("customer already exists!", outContent.toString().trim());
}
//Test task 7
@Test
public void testCheckValidityValid() {
    AdvertisingPlatform platform = new AdvertisingPlatform(new HashSet<>(Arrays.asList("badword")));
    String advertisement = "Great product! Buy now."; 

    assertTrue(platform.checkValidity(advertisement));
}


@Test
public void testCheckValidityTooLong() {
    AdvertisingPlatform platform = new AdvertisingPlatform(preferences); 
    String advertisement = "one two three four five six seven eight nine ten eleven twelve thirteen fourteen fifteen sixteen seventeen eighteen nineteen twenty one";
    
    boolean result = platform.checkValidity(advertisement);
    System.out.println("Advertisement validity: " + result); // Should print false
    assertFalse(result);
}



@Test
public void testCheckValidityForbiddenWord() {
    AdvertisingPlatform platform = new AdvertisingPlatform(new HashSet<>(Arrays.asList("badword")));
    String advertisement = "Check out this product. It's not a badword."; 

    assertFalse(platform.checkValidity(advertisement));
}



}