package com.mallang.backend.service;

import com.mallang.backend.domain.Admin;
import com.mallang.backend.domain.Notice;
import com.mallang.backend.dto.AdminDTO;
import com.mallang.backend.repository.AdminRepository;
import com.mallang.backend.repository.NoticeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private NoticeRepository noticeRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterAdmin() {
        // Arrange
        String adminId = "admin123";
        String password = "adminPass";

        when(adminRepository.findByAdminId(adminId)).thenReturn(Optional.empty());

        // Act
        adminService.registerAdmin(adminId, password);

        // Assert
        verify(adminRepository, times(1)).save(any(Admin.class));
    }

    @Test
    void testRegisterAdminWhenIdAlreadyExists() {
        // Arrange
        String adminId = "admin123";
        String password = "adminPass";

        Admin existingAdmin = new Admin();
        existingAdmin.setAdminId(adminId);
        when(adminRepository.findByAdminId(adminId)).thenReturn(Optional.of(existingAdmin));

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            adminService.registerAdmin(adminId, password);
        });
        assertEquals("이미 사용 중인 아이디입니다.", thrown.getMessage());
    }

    @Test
    void testAuthenticateAdmin() {
        // Arrange
        String adminId = "admin123";
        String password = "adminPass";

        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setPassword(password);
        when(adminRepository.findByAdminIdAndPassword(adminId, password)).thenReturn(Optional.of(admin));

        // Act
        adminService.authenticateAdmin(adminId, password);

        // Assert
        verify(adminRepository, times(1)).findByAdminIdAndPassword(adminId, password);
    }

    @Test
    void testAuthenticateAdminWhenInvalidCredentials() {
        // Arrange
        String adminId = "admin123";
        String password = "adminPass";

        when(adminRepository.findByAdminIdAndPassword(adminId, password)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            adminService.authenticateAdmin(adminId, password);
        });
        assertEquals("아이디 또는 비밀번호가 잘못되었습니다.", thrown.getMessage());
    }

    @Test
    void testGetAdminById() {
        // Arrange
        String adminId = "admin123";
        Admin admin = new Admin();
        admin.setAdminName("John Doe");
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));

        // Act
        AdminDTO adminDTO = adminService.getAdminById(adminId);

        // Assert
        assertNotNull(adminDTO);
        assertEquals("John Doe", adminDTO.getAdminName());
    }

    @Test
    void testGetAdminByIdWhenAdminNotFound() {
        // Arrange
        String adminId = "admin123";
        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            adminService.getAdminById(adminId);
        });
        assertEquals("해당 ID의 관리자를 찾을 수 없습니다: admin123", thrown.getMessage());
    }

    @Test
    void testDeleteAdmin() {
        // Arrange
        String adminName = "admin123";
        when(adminRepository.existsById(adminName)).thenReturn(true);

        // Act
        adminService.deleteAdmin(adminName);

        // Assert
        verify(adminRepository, times(1)).deleteById(adminName);
    }

    @Test
    void testDeleteAdminWhenAdminNotFound() {
        // Arrange
        String adminName = "admin123";
        when(adminRepository.existsById(adminName)).thenReturn(false);

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            adminService.deleteAdmin(adminName);
        });
        assertEquals("해당 ID의 관리자를 찾을 수 없습니다: admin123", thrown.getMessage());
    }

    @Test
    void testRegisterNotice() throws Exception {
        // Arrange
        String title = "New Notice";
        String writer = "admin";
        String email = "admin@domain.com";
        String password = "password";
        Boolean isSecret = false;
        String content = "Notice content";
        String link = "http://example.com";

        // Mock file upload logic
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("file.jpg");

        // Act
        adminService.registerNotice(title, writer, email, password, isSecret, file, file, content, link);

        // Assert
        verify(noticeRepository, times(1)).save(any(Notice.class));
    }

    // Additional tests for other methods can follow the same pattern.

}