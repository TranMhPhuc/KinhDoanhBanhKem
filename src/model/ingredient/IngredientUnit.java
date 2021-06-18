package model.ingredient;

public enum IngredientUnit {
    KILOGAM {
        @Override
        public String toString() {
            return "Kilôgam";
        }
    },
    LIT {
        @Override
        public String toString() {
            return "Lít";
        }
    },
    QUA {
        @Override
        public String toString() {
            return "Quả";
        }
    };
}
