package me.scaliermaelstrom;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;

import javax.swing.GroupLayout;

import me.scaliermaelstrom.network.server.Server;

public class TankGame extends ApplicationAdapter {
	SpriteBatch batch;

	public static Texture tankTexture;
	private Skin skin;
	private Stage stage;
	private Texture border;

	@Override
	public void create () {
		batch = new SpriteBatch();
		tankTexture = new Texture("tank.png");
		border = new Texture("border.png");
	    skin = new Skin(Gdx.files.internal("skin/cloud-form-ui.json"));

	    stage = new Stage();
        Table table = new Table();
        table.setFillParent(true);

        Label title = new Label("Tank game", skin, "white-title");
        table.add(title).padBottom(10);
        table.row();

        Table background = new Table();
        background.pad(20);
        background.background(new NinePatchDrawable(new NinePatch(border, 3, 3, 3, 3)));

        Label hostGameTitle = new Label("Host game", skin, "white-title");
        hostGameTitle.setColor(Color.WHITE);
        background.add(hostGameTitle).padBottom(5);
        background.row();

        TextButton host = new TextButton("Start", skin);
        background.add(host).padBottom(20);
        background.row();

        Label joinGameTitle = new Label("Join game", skin, "white-title");
        joinGameTitle.setColor(Color.WHITE);
        background.add(joinGameTitle).padBottom(5);
        background.row();

        TextField id = new TextField("", skin);
        id.setOnlyFontChars(true);
        id.setMessageText("Enter game id");
        id.setAlignment(Align.center);
        background.add(id);
        background.row();

        TextButton join = new TextButton("Join", skin);
        background.add(join);
        background.row();

        table.add(background);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

        host.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Create server, start game
                Gdx.app.log("TankGame", "clicked host");
            }

        });

        join.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Find server, start game
                Gdx.app.log("TankGame", "clicked join");
            }

        });
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		batch.begin();
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		tankTexture.dispose();
		border.dispose();
		stage.dispose();
	}
}
