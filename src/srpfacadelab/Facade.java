package srpfacadelab;

import java.util.List;

public class Facade {

    public void useItem(RpgPlayer player, Item item) {
        if (item.getName().equals("Stink Bomb"))
        {
            List<IEnemy> enemies = player.getGameEngine().getEnemiesNear(player);

            for (IEnemy enemy: enemies){
                enemy.takeDamage(100);
            }
        }
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

    public boolean pickUpItem(RpgPlayer player, Item item) {
        int weight = player.calculateInventoryWeight();
        if (weight + item.getWeight() > player.getCarryingCapacity())
            return false;

        if (item.isUnique() && player.checkIfItemExistsInInventory(item))
            return false;

        // Don't pick up items that give health, just consume them.
        if (item.getHeal() > 0) {
            player.setHealth(player.getHealth() + item.getHeal());

            if (player.getHealth() > player.getMaxHealth())
                player.setHealth(player.getMaxHealth());

            if (item.getHeal() > 500) {
                player.getGameEngine().playSpecialEffect("green_swirly");
            }

            return true;
        }

        if(item.isRare() && item.isUnique()){
            player.getGameEngine().playSpecialEffect("blue_swirly");
        }

        else if (item.isRare())
            player.getGameEngine().playSpecialEffect("cool_swirly_particles");

        player.addToInventory(item);

        player.calculateStats();

        return true;
    }

}
