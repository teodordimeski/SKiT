package service;

import model.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.CourseRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void findActiveCourses_returnsOnlyActiveCourses() {
        Course activeCourse = new Course(1L, "Software Testing", 6, true);
        Course inactiveCourse = new Course(2L, "Legacy Systems", 4, false);
        Course secondActiveCourse = new Course(3L, "Quality Engineering", 5, true);

        when(courseRepository.findAll()).thenReturn(List.of(activeCourse, inactiveCourse, secondActiveCourse));

        List<Course> result = courseService.findActiveCourses();

        assertEquals(2, result.size());
        assertSame(activeCourse, result.get(0));
        assertSame(secondActiveCourse, result.get(1));
        verify(courseRepository).findAll();
    }

    @Test
    void findActiveCourses_whenRepositoryIsEmpty_returnsEmptyList() {
        when(courseRepository.findAll()).thenReturn(List.of());

        List<Course> result = courseService.findActiveCourses();

        assertTrue(result.isEmpty());
        verify(courseRepository).findAll();
    }

    @Test
    void findById_whenCourseExists_returnsCourse() {
        Course course = new Course(1L, "Mockito", 5, true);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course result = courseService.findById(1L);

        assertSame(course, result);
        verify(courseRepository).findById(1L);
    }

    @Test
    void findById_whenCourseDoesNotExist_throwsRuntimeException() {
        when(courseRepository.findById(77L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> courseService.findById(77L));

        assertEquals("Course not found", exception.getMessage());
        verify(courseRepository).findById(77L);
    }

    @Test
    void createCourse_withValidInput_savesAndReturnsCourse() {
        Course saved = new Course(10L, "DevOps", 6, true);
        when(courseRepository.save(org.mockito.ArgumentMatchers.any(Course.class))).thenReturn(saved);

        Course result = courseService.createCourse("DevOps", 6);

        ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository).save(captor.capture());
        Course toSave = captor.getValue();
        assertEquals("DevOps", toSave.getName());
        assertEquals(6, toSave.getCredits());
        assertTrue(toSave.isActive());
        assertNull(toSave.getId());
        assertSame(saved, result);
    }

    @Test
    void createCourse_withNullName_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> courseService.createCourse(null, 6));

        assertEquals("Course name is required", exception.getMessage());
        verify(courseRepository, never()).save(org.mockito.ArgumentMatchers.any(Course.class));
    }

    @Test
    void createCourse_withBlankName_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> courseService.createCourse("   ", 6));

        assertEquals("Course name is required", exception.getMessage());
        verify(courseRepository, never()).save(org.mockito.ArgumentMatchers.any(Course.class));
    }

    @Test
    void createCourse_withZeroCredits_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> courseService.createCourse("JUnit", 0));

        assertEquals("Credits must be positive", exception.getMessage());
        verify(courseRepository, never()).save(org.mockito.ArgumentMatchers.any(Course.class));
    }

    @Test
    void createCourse_withNegativeCredits_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> courseService.createCourse("JUnit", -2));

        assertEquals("Credits must be positive", exception.getMessage());
        verify(courseRepository, never()).save(org.mockito.ArgumentMatchers.any(Course.class));
    }

    @Test
    void deleteCourse_whenCourseExists_deletesByResolvedId() {
        Course course = new Course(15L, "TDD", 5, true);
        when(courseRepository.findById(15L)).thenReturn(Optional.of(course));

        courseService.deleteCourse(15L);

        verify(courseRepository).findById(15L);
        verify(courseRepository).deleteById(15L);
    }

    @Test
    void deleteCourse_whenCourseDoesNotExist_throwsRuntimeExceptionAndDoesNotDelete() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> courseService.deleteCourse(99L));

        assertEquals("Course not found", exception.getMessage());
        verify(courseRepository).findById(99L);
        verify(courseRepository, never()).deleteById(org.mockito.ArgumentMatchers.anyLong());
    }
}

