package org.genshin.scrollninja;

//========================================
// インポート
//========================================
import java.util.ArrayList;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

//========================================
// クラス宣言
//========================================
public class StageObject extends ObJectBase {
	public static final int ROCK			= 0;
	public static final int HOUSE			= 1;
	
	private int			type;			// タイプ
	private int			number;			// 管理番号
	private Vector2		position;		// 座標

	// コンストラクタ
	StageObject(int Type, int num, Vector2 pos) {
		sprite		= new ArrayList<Sprite>();
		sensor		= new ArrayList<Fixture>();
		
		number		= num;
		type		= Type;
		position	= new Vector2(pos);

		Create();
	}
	StageObject(int Type, int num, float x, float y) {
		sprite		= new ArrayList<Sprite>();
		sensor		= new ArrayList<Fixture>();
		
		number		= num;
		type		= Type;
		position	= new Vector2(x,y);

		Create();
	}

	public void Create() {
		switch(type) {
		case ROCK:
			Texture texture = new Texture(Gdx.files.internal("data/stage_object.png"));
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			TextureRegion tmpRegion = new TextureRegion(texture, 0, 128, 256, 256);
			sprite.add(new Sprite(tmpRegion));
			sprite.get(0).setOrigin(0.0f, 0.0f);
			sprite.get(0).setScale(0.1f);
			
			// 当たり判定読み込み
			BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/stageObject.json"));

			// Bodyのタイプを設定 Staticは動かない物体
			BodyDef bd = new BodyDef();
			bd.type = BodyType.StaticBody;

			// Bodyの設定を設定
			FixtureDef fd	= new FixtureDef();
			fd.density		= 1000;				// 密度
			fd.friction		= 0;				// 摩擦
			fd.restitution	= 0;				// 反発係数

			body = GameMain.world.createBody(bd);

			// 各種設定を適用。引数は　Body、JSON中身のどのデータを使うか、FixtureDef、サイズ
			loader.attachFixture(body, "gravestone", fd, sprite.get(0).getWidth() * 0.1f);
			body.setTransform(position, 0);
			System.out.println(body.getPosition());
			System.out.println(sprite.get(0).getOriginX());
			System.out.println(sprite.get(0).getOriginY());
			
			break;
			
		case HOUSE:
			break;
		}
	}
	
	@Override
	public void collisionDispatch(ObJectBase obj, Contact contact) {
		obj.collisionNotify(this, contact);
	}
	
	@Override
	public void collisionNotify(Background obj, Contact contact){}	
	
	@Override
	public void collisionNotify(Player obj, Contact contact){}
	
	@Override
	public void collisionNotify(Enemy obj, Contact contact){}
	
	@Override
	public void collisionNotify(Effect obj, Contact contact){}
	
	@Override
	public void collisionNotify(Item obj, Contact contact){}
	
	@Override
	public void collisionNotify(StageObject obj, Contact contact){}
	
	@Override
	public void collisionNotify(Weapon obj, Contact contact){}

	//************************************************************
	// Get
	// ゲッターまとめ
	//************************************************************
	public StageObject GetStageObject() { return this; }
	public int GetType(){ return type; }
	public int GetNum(){ return number; }

	//************************************************************
	// Set
	// セッターまとめ
	//************************************************************
	public void SetNum(int num){ number = num; }
	
}
