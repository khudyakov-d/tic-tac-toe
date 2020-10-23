package ccfit.nsu.ru.khudyakov.tic_tok_toe;

public class MvpPresenter<View extends MvpView> {

    protected View view;

    public void attachView(View view) {
        this.view = view;
        onViewReady();
    }

    public void detachView() {
        view = null;
    }

    protected void  onViewReady() {
    }
}
