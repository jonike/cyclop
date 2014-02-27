package org.cyclop.web.pages.main;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.cyclop.web.pages.authenticate.AuthenticatePage;
import org.cyclop.web.pages.parent.ParentPage;
import org.cyclop.web.panels.commander.CommanderPanel;
import org.cyclop.web.panels.favourites.FavouritesPanel;
import org.cyclop.web.panels.history.HistoryPanel;

/** @author Maciej Miklas */
@AuthorizeInstantiation(Roles.ADMIN)
public class MainPage extends ParentPage {

	private final TabSupport tabSupport;

	private static final JavaScriptResourceReference JS_MAIN = new JavaScriptResourceReference(MainPage.class,
			"main.js");

	// TODO enable/disable history from properties

	public MainPage(PageParameters params) {
		tabSupport = new TabSupport();
		initQueryEditorTab(params);
		initHistoryTab();
		initFavouritesTab();
		initLogout();
	}

	private void initLogout() {
		Link<Void> logOut = new Link<Void>("logOut") {
			@Override
			public void onClick() {
				getSession().invalidate();
				setResponsePage(AuthenticatePage.class);
			}
		};
		add(logOut);
	}

	private void initQueryEditorTab(PageParameters params) {
		CommanderPanel queryEditorPanel = new CommanderPanel("queryEditorPanel", params);
		add(queryEditorPanel);
		tabSupport.registerSaticTab(".cq-tabQueryEditor");
	}

	private void initFavouritesTab() {
		FavouritesPanel favourites = new FavouritesPanel("favouritesPanel");
		add(favourites);
		tabSupport.registerReloadableTab(favourites, ".cq-tabFavourites");
	}


	private void initHistoryTab() {
		HistoryPanel historyPanel = new HistoryPanel("historyPanel");
		add(historyPanel);
		tabSupport.registerReloadableTab(historyPanel, ".cq-tabHistory");
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		response.render(JavaScriptReferenceHeaderItem.forReference(JS_MAIN));
		tabSupport.renderHead(response);
	}

}