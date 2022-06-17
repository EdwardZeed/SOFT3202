package presenter;

import model.*;
import view.BeginningWindowView;

import java.io.IOException;

/**
 * The mediator between the beginning window and the model.
 */
public class BeginningWindowPresenter {
    private BeginningWindowView view;
    private Model model;

    /**
     * Instantiates a new Beginning window presenter.
     *
     * @param view the view
     */
    public BeginningWindowPresenter(BeginningWindowView view) {
        this.view = view;
    }

    /**
     * Sets status.
     * If currencyOnline is true, then use online implementation for CurrencyScoop, else use offline implementation.
     * If pastebinOnline is true, then use online implementation for Pastebin, else use offline implementation.
     *
     * @param currencyOnline the currency online
     * @param pastebinOnline the pastebin online
     */
    public void setStatus(boolean currencyOnline, boolean pastebinOnline) {

        CurrencyScoop input;
        Pastebin output;
        if (currencyOnline) {
            input = new CurrencyScoopAPIOnline();
        }
        else {
            input = new CurrencyScoopAPIOffline();
        }
        if (pastebinOnline) {
            output = new PastebinAPIOnline();
        }
        else {
            output = new PastebinAPIOffline();
        }
        model = new Model(input, output);
        System.out.println(model);
        StageManagement.model = model;
    }

    /**
     * Sets threshold. if the input is not a number, display an error message. if the input is not between 0.1 and 1.0, then display an error message.
     *
     * @param threshold the threshold
     */
    public void setThreshold(String threshold) {
        double thresholdDouble = 0;
        try {
            thresholdDouble = Double.parseDouble(threshold);
        } catch (NumberFormatException e) {
            this.view.displayError("Please enter a number");
            return;
        }

        boolean setSuccess = model.setThreshold(thresholdDouble);
        if (!setSuccess) {
            this.view.displayError("Please enter a valid number between 0.1 and 1.0");
            return;
        }

        try {
            this.view.buildMainWindow();
        } catch (IOException e) {
            this.view.displayError("Error building main window");
        }
    }
}
