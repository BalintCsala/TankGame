package me.scaliermaelstrom;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Tank {

    public Vector2 position;
    private Texture texture;

    public Tank(Vector2 position, Texture texture) {
        this.position = position;
        this.texture = texture;
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, position.x, position.y);
    }

}
