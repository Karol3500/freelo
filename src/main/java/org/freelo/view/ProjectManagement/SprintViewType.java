package org.freelo.view.ProjectManagement;

import com.vaadin.navigator.View;
import org.freelo.view.tasks.TaskPage;

/**
 * Created by Konrad on 2015-01-13.
 */
public enum SprintViewType {
    TASKPAGE(TaskPage.NAME, TaskPage.class);

    private final String viewName;
    private final Class<? extends View> viewClass;

    SprintViewType(final String viewName,
                   final Class<? extends View> viewClass) {
        this.viewName = viewName;
        this.viewClass = viewClass;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }


    public static SprintViewType getByViewName(final String viewName) {
        SprintViewType result = null;
        for (SprintViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}

