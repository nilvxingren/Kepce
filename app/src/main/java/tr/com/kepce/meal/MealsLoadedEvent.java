package tr.com.kepce.meal;

import tr.com.kepce.common.PagedList;

public class MealsLoadedEvent {

    private PagedList<Meal> mMeals;

    public MealsLoadedEvent(PagedList<Meal> meals) {
        mMeals = meals;
    }

    public PagedList<Meal> getMeals() {
        return mMeals;
    }
}
