package com.chukslabs.trical.data;

/**
 * Created by echuquilin on 27/06/17.
 */
public class MyCoursesLang {

    public static final String EXTRA_STRING_INST = "institution";
    public static final String EXTRA_OBJECT_COURSE = "courseObject";
    public static final String EXTRA_OBJECT_EXAMS = "examsObject";

    // Search
    public static final String SEARCH_HINT_INSTITUTIONS = "Buscar Centro de Estudios";

    // Snackbars
    public static final String SNACKBAR_OK = "OK";
    public static final String SNACKBAR_UNDO = "Deshacer";
    public static final String SNACKBAR_COURSE_ADDED_MSG = "Se agregó el curso %s correctamente";
    public static final String SNACKBAR_COURSE_REMOVED_MSG = "Se eliminó el curso %s";

    // Dialogs
    public static final String DIALOG_OK = "OK";
    public static final String DIALOG_ERROR = "ERROR";
    public static final String DIALOG_CANCEL = "Cancelar";
    public static final String DIALOG_REMOVE_TITLE = "Eliminar Curso";
    public static final String DIALOG_REMOVE_MESSAGE = "¿Seguro/a desea eliminar %s?";
    public static final String DIALOG_REMOVE_POSITIVE = "Eliminar";
    public static final String DIALOG_COURSE_DUPLICATED = "Ya existe un curso con ese nombre en ese centro de estudios en el mismo periodo";
    public static final String DIALOG_COURSE_DUPLICATED_CODE = "Ya existe un curso con ese código y nombre en ese centro de estudios en el mismo periodo";
    public static final String DIALOG_COURSE_GRADES_ERROR = "La mayor nota obtenible no puede ser menor que la mínima nota para aprobar";

    // Action Bar titles
    public static final String MY_COURSES_TITLE = "Mis Cursos";
    public static final String ADD_COURSE_TITLE = "Nuevo Curso";
    public static final String ADD_EXAMS_TITLE = "Añadir Evaluaciones";
    public static final String UPDATE_EXAMS_TITLE = "Modificar Evaluaciones";
    public static final String INSTITUTIONS_TITLE = "Centros de Estudio";

    // Courses list Screen
    public static final String SUBTITLE_NO_EXAMS = "No hay evaluaciones registradas";
    public static final String SUBTITLE_IN_PROGRESS = "Curso en proceso";
    public static final String SUBTITLE_APPROVED = "Curso Aprobado! :D  Nota: %s";
    public static final String SUBTITLE_DESAPPROVED = "Curso Desaprobado :(  Nota: %s";

    // Add Course Screen
    public static final String ADD_COURSE_INST = "Centro de Estudios";

    // Institutions Screen
    public static final String ADD_INST_BUTTON = "Añadir \"%s\" como nuevo Centro de Estudios";

    // Course Detail Screen
    public static final String DETAIL_GRADE_NAME = "Nota";
    public static final String DETAIL_FINAL_GRADE_LEFT = "Faltan Notas";

}
