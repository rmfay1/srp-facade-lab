package srpfacadelab;

public class DamageController {

    private IGameEngine gameEngine;

    public DamageController(IGameEngine gameEngine)
    {
        this.gameEngine = gameEngine;
    }

    public void takeDamage(RpgPlayer player, int damage) {


        if (damage < player.getArmour()) {
            player.getGameEngine().playSpecialEffect("parry");
        }

        int damageToDeal;

        if (player.calculateInventoryWeight() > (0.5 * player.getCarryingCapacity())){
            damageToDeal = (int)(0.75 * damage) - player.getArmour();
        }
        else{
            damageToDeal = damage - player.getArmour();
        }

        player.setHealth(player.getHealth() - damageToDeal) ;

        player.getGameEngine().playSpecialEffect("lots_of_gore");
    }


}
