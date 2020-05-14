package srpfacadelab;

public class Facade {

    private IGameEngine gameEngine;

    private ItemController itemActions;

    private DamageController damageController;

    public Facade(IGameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.itemActions = new ItemController(gameEngine);
        this.damageController = new DamageController(gameEngine);
    }

    public void useItem(Item item, RpgPlayer player) {
        this.itemActions.useItem(player,item);
    }

    public boolean pickUpItem(Item item, RpgPlayer player) {
        return this.itemActions.pickUpItem(player,item);
    }

    public void takeDamage(int damage, RpgPlayer player) {
        this.damageController.takeDamage(player,damage);
    }



}
