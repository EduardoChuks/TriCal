package com.chukslabs.trical.util;

import com.chukslabs.trical.data.MyCoursesLang;
import com.chukslabs.trical.model.CalcMethod;
import com.chukslabs.trical.model.Course;
import com.chukslabs.trical.model.Exam;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by educhuks on 10/4/17.
 */

public class GradeUtils {

    public static String getCourseSubtitle(Course course) {
        List<Exam> exams = course.getExams();
        if (exams.size() > 0) {
            if (course.getFinalGrade() != -1) {
                return course.getFinalGrade() >= course.getMinGrade() ?
                        String.format(MyCoursesLang.SUBTITLE_APPROVED, course.getFinalGrade()) :
                        String.format(MyCoursesLang.SUBTITLE_DESAPPROVED, course.getFinalGrade()) ;
            } else {
                return MyCoursesLang.SUBTITLE_IN_PROGRESS;
            }
        } else {
            return MyCoursesLang.SUBTITLE_NO_EXAMS;
        }
    }

    public static String getExamAvgTitle(Exam exam, CalcMethod calcMethod) {
        boolean gradesLeft = false;
        for (Integer grade : exam.getGrades()) {
            if (grade == -1) {
                gradesLeft = true;
                break;
            }
        }
        if (!gradesLeft) {
            double average = getGradeAverage(exam.getGrades(), exam.isRemoveMin(), calcMethod);
            return String.valueOf(average);
        } else {
            return MyCoursesLang.DETAIL_FINAL_GRADE_LEFT;
        }
    }

    public static int getFinalGrade(Course course) {
        List<Exam> exams = course.getExams();
        if (exams.size() > 0 && hasCompletedGrades(exams)) {
            return (int) calculateFinalGrade(course);
        } else {
            return -1;
        }
    }

    private static boolean hasCompletedGrades(List<Exam> exams) {
        for (Exam exam : exams) {
            for (Integer grade : exam.getGrades()) {
                if (grade == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    private static long calculateFinalGrade(Course course) {
        List<Exam> exams = course.getExams();
        double finalGrade;
        double totalSum = 0;
        int totalQuant = 0;
        for (Exam exam : exams) {
            totalQuant += exam.getWeight();
            if (exam.getQuantity() > 1) {
                totalSum += (getGradeAverage(exam.getGrades(), exam.isRemoveMin(), course.getCalcMethod()) * exam.getWeight());
            } else {
                totalSum += (exam.getGrades().get(0) * exam.getWeight());
            }
        }
        finalGrade = totalSum / totalQuant;
        return Math.round(finalGrade);
    }

    public static double getGradeAverage(List<Integer> grades, boolean removeMin, CalcMethod calcMethod) {
        double tempSum = 0;
        double tempQuant = grades.size();
        double minGrade = 1000;
        for (Integer grade : grades) {
            tempSum += grade;
            if (minGrade > grade) {
                minGrade = grade;
            }
        }
        if (removeMin) {
            tempSum -= minGrade;
            tempQuant--;
        }
        if (calcMethod.getRoundedToNext() != null) {
            return Math.round(tempSum / tempQuant);
        } else {
            return truncateDecimal(tempSum / tempQuant, calcMethod.getDecimalsQuant()).doubleValue();
        }
    }

    private static BigDecimal truncateDecimal(double num, int numberofDecimals) {
        if (num > 0) {
            return new BigDecimal(String.valueOf(num)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(num)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
        }
    }

}
