package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class OrganizationTest {
    private Organization organization;
    
    @Before
    public void setUp() {
        organization = new Organization();
    }
    
    @Test
    public void testConstructor() {
        assertNotNull("Organization should not be null", organization);
        assertNotNull("Employees list should be initialized", organization.getEmployees());
        assertNotNull("Rooms list should be initialized", organization.getRooms());
        assertFalse("Employee list should not be empty", organization.getEmployees().isEmpty());
        assertFalse("Rooms list should not be empty", organization.getRooms().isEmpty());
    }
    
    @Test
    public void testGetEmployee_Success() {
        try {
            Person employee = organization.getEmployee("Greg Gay");
            assertNotNull("Should find employee Greg Gay", employee);
            assertEquals("Name should match", "Greg Gay", employee.getName());
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test(expected = Exception.class)
    public void testGetEmployee_NonExistent() throws Exception {
        organization.getEmployee("Nonexistent Person");
    }
    
    @Test
    public void testGetRoom_Success() {
        try {
            Room room = organization.getRoom("2A01");
            assertNotNull("Should find room 2A01", room);
            assertEquals("Room ID should match", "2A01", room.getID());
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test(expected = Exception.class)
    public void testGetRoom_NonExistent() throws Exception {
        organization.getRoom("NonexistentRoom");
    }
    
    @Test
    public void testEmployeeList_Integrity() {
        assertEquals("Should have 5 employees", 5, organization.getEmployees().size());
        assertTrue("Should contain Greg Gay", 
            organization.getEmployees().stream().anyMatch(p -> p.getName().equals("Greg Gay")));
        assertTrue("Should contain Manton Matthews", 
            organization.getEmployees().stream().anyMatch(p -> p.getName().equals("Manton Matthews")));
    }
    
    @Test
    public void testRoomList_Integrity() {
        assertEquals("Should have 5 rooms", 5, organization.getRooms().size());
        assertTrue("Should contain room 2A01", 
            organization.getRooms().stream().anyMatch(r -> r.getID().equals("2A01")));
        assertTrue("Should contain room 2A05", 
            organization.getRooms().stream().anyMatch(r -> r.getID().equals("2A05")));
    }
    
    @Test
    public void testGetEmployee_CaseInsensitive() {
        try {
            Person employee = organization.getEmployee("GREG GAY");
            fail("Should throw exception for case mismatch");
        } catch (Exception e) {
            assertTrue("Should mention employee does not exist", 
                      e.getMessage().contains("does not exist"));
        }
    }
    
    @Test
    public void testGetRoom_CaseInsensitive() {
        try {
            Room room = organization.getRoom("2a01");
            fail("Should throw exception for case mismatch");
        } catch (Exception e) {
            assertTrue("Should mention room does not exist", 
                      e.getMessage().contains("does not exist"));
        }
    }
    
    @Test(expected = Exception.class)
    public void testGetEmployee_EmptyName() throws Exception {
        organization.getEmployee("");
    }
    
    @Test(expected = Exception.class)
    public void testGetRoom_EmptyId() throws Exception {
        organization.getRoom("");
    }
}
