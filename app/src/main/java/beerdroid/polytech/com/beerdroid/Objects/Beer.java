package beerdroid.polytech.com.beerdroid.Objects;


/**
 * Created by Menros on 29/12/2017.
 */

public class Beer {
    private int id;
    private String name;
    private String tagline;
    private String first_brewed;
    private String description;
    private String image_url;
    private String brewers_tips;
    private String contributed_by;
    private String ingredientsMalt;
    private String ingredientsHops;
    private String ingredientYeast;

    public Beer(int id, String name, String tagline, String first_brewed, String description, String image_url, String brewers_tips, String contributed_by, String ingredientsMalt, String ingredientsHops, String ingredientYeast) {
        this.id = id;
        this.name = name;
        this.tagline = tagline;
        this.first_brewed = first_brewed;
        this.description = description;
        this.image_url = image_url;
        this.brewers_tips = brewers_tips;
        this.contributed_by = contributed_by;
        this.ingredientsMalt = ingredientsMalt;
        this.ingredientsHops = ingredientsHops;
        this.ingredientYeast = ingredientYeast;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTagline() {
        return tagline;
    }

    public String getFirst_brewed() {
        return first_brewed;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getBrewers_tips() {
        return brewers_tips;
    }

    public String getContributed_by() {
        return contributed_by;
    }

    public String getIngredientsMalt() {
        return ingredientsMalt;
    }

    public String getIngredientsHops() {
        return ingredientsHops;
    }

    public String getIngredientYeast() {
        return ingredientYeast;
    }
}
