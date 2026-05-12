package pl.luki2183.farmManager.fieldInfo.model;

/**
 * Enumeration of crop and grain types that can be assigned to a farm field.
 *
 * <p>Used as the grain type value in {@link FieldInfoEntity} and related DTOs.
 * {@code DEFAULT} serves as the fallback value when no specific crop has been assigned.</p>
 */
public enum Grain {
    DEFAULT, // domyślne
    WHEAT, // przenica
    BARLEY, // jęczmień
    HYBRID_BARLEY, // jęczmień hybrydowy
    COLZA, // rzepak
    CARROT, // marchew
    CORN, // kukurydza
    OATS, // owies
    NAKED_OATS, // owies nagi
    ROUGH_OATS, // owies szorstki
    ONION, // cebula
    PEAS, // groch
    POTATO, // ziemniak
    RED_BEET, // burak czerwony
    SORGHUM, // sorgo
    SOYBEANS, // soja
    SUGAR_BEET, // burak cukrowy
    SUNFLOWER, // słonecznik
    LUPINE, // łubin
    TRITICALE, // przenżyto
    RYE, // żyto
    HYBRID_RYE, // żyto hybrydowe
    SYNTHETIC_RYE, // żyto syntetyczne
    BROAD_BEAN, // bobik
    COVERED_VETCH, // wyka siewna
    CEREAL_MIXTURE // mieszanka zbożowa
}
