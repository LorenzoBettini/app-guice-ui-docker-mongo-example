package com.examples.school.guice;

import com.examples.school.controller.SchoolController;
import com.examples.school.view.StudentView;

public interface SchoolControllerFactory {

	SchoolController create(StudentView view);

}
