package com.virus.model;

import com.virus.model.card.Medicine;
import com.virus.model.card.NormalCard;
import com.virus.model.card.Organ;
import com.virus.model.card.Virus;
import com.virus.model.enums.TypeOfOrgan;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Body {

    private Stack<NormalCard> heart;
    private Stack<NormalCard> stomach;
    private Stack<NormalCard> brain;
    private Stack<NormalCard> bone;
    private Stack<NormalCard> multicolor;
    private boolean savedHeart;
    private boolean savedStomach;
    private boolean savedBrain;
    private boolean savedBone;
    private boolean savedMulticolor;

    public Body() {
        this.heart = new Stack<>();
        this.stomach = new Stack<>();
        this.brain = new Stack<>();
        this.bone = new Stack<>();
        this.multicolor = new Stack<>();
        this.savedHeart = false;
        this.savedStomach = false;
        this.savedBrain = false;
        this.savedBone = false;
        this.savedMulticolor = false;
    }

    public Stack<NormalCard> getHeart() {
        return heart;
    }

    public Stack<NormalCard> getStomach() {
        return stomach;
    }

    public Stack<NormalCard> getBrain() {
        return brain;
    }

    public Stack<NormalCard> getBone() {
        return bone;
    }

    public Stack<NormalCard> getMulticolor() {
        return multicolor;
    }

    public void setHeart(Stack<NormalCard> heart) {
        this.heart = heart;
    }

    public void setStomach(Stack<NormalCard> stomach) {
        this.stomach = stomach;
    }

    public void setBrain(Stack<NormalCard> brain) {
        this.brain = brain;
    }

    public void setBone(Stack<NormalCard> bone) {
        this.bone = bone;
    }

    public void setMulticolor(Stack<NormalCard> multicolor) {
        this.multicolor = multicolor;
    }

    public boolean isHealthy(TypeOfOrgan type) {
        if (type.equals(TypeOfOrgan.HEART)) {
            return !this.heart.empty() && (this.heart.peek() instanceof Organ || this.heart.peek() instanceof Medicine);
        } else if (type.equals(TypeOfOrgan.STOMACH)) {
            return !this.stomach.empty() && (this.stomach.peek() instanceof Organ || this.stomach.peek() instanceof Medicine);
        } else if (type.equals(TypeOfOrgan.BRAIN)) {
            return !this.brain.empty() && (this.brain.peek() instanceof Organ || this.brain.peek() instanceof Medicine);
        } else if (type.equals(TypeOfOrgan.BONE)) {
            return !this.bone.empty() && (this.bone.peek() instanceof Organ || this.bone.peek() instanceof Medicine);
        } else {
            return !this.multicolor.empty() && (this.multicolor.peek() instanceof Organ || this.multicolor.peek() instanceof Medicine);
        }
    }

    private boolean isNotSaved(TypeOfOrgan type) {
        if (type.equals(TypeOfOrgan.HEART)) {
            return !this.savedHeart;
        } else if (type.equals(TypeOfOrgan.STOMACH)) {
            return !this.savedStomach;
        } else if (type.equals(TypeOfOrgan.BRAIN)) {
            return !this.savedBrain;
        } else if (type.equals(TypeOfOrgan.BONE)) {
            return !this.savedBone;
        }
        return !this.savedMulticolor;

    }

    private void setStack(Stack<NormalCard> stack, TypeOfOrgan type) {
        if (type.equals(TypeOfOrgan.HEART)) {
            this.setHeart(stack);
        } else if (type.equals(TypeOfOrgan.STOMACH)) {
            this.setStomach(stack);
        } else if (type.equals(TypeOfOrgan.BRAIN)) {
            this.setBrain(stack);
        } else if (type.equals(TypeOfOrgan.BONE)) {
            this.setBone(stack);
        } else {
            this.setMulticolor(stack);
        }
    }

    private Stack<NormalCard> getStackByType(TypeOfOrgan type) {
        if (type.equals(TypeOfOrgan.HEART)) {
            return this.getHeart();
        } else if (type.equals(TypeOfOrgan.STOMACH)) {
            return this.getStomach();
        } else if (type.equals(TypeOfOrgan.BRAIN)) {
            return this.getBrain();
        } else if (type.equals(TypeOfOrgan.BONE)) {
            return this.getBone();
        } else {
            return this.getMulticolor();
        }
    }


    public boolean addOrgan(Organ organ) {
        if (organ.getType().equals(TypeOfOrgan.HEART)) {
            return addOrganIfValid(savedHeart, heart, organ);
        }
        if (organ.getType().equals(TypeOfOrgan.STOMACH)) {
            return addOrganIfValid(savedStomach, stomach, organ);
        }
        if (organ.getType().equals(TypeOfOrgan.BRAIN)) {
            return addOrganIfValid(savedBrain, brain, organ);
        }
        if (organ.getType().equals(TypeOfOrgan.BONE)) {
            return addOrganIfValid(savedBone, bone, organ);
        }
        if (organ.getType().equals(TypeOfOrgan.MULTICOLOR)) {
            return addOrganIfValid(savedMulticolor, multicolor, organ);
        }
        return false;
    }

    // Me fijo si esta salvado o vacia
    private boolean addOrganIfValid(boolean saved, Stack<NormalCard> stack, Organ card) {
        if (!saved) {
            if (stack.empty()) {
                stack.add(card);
                return true;
            }
        }
        return false;
    }



    public boolean addMedicineCard(NormalCard card, TypeOfOrgan type, DiscardPile discardPile)  {
        if (card instanceof Medicine medicineCard)   {

            if (type.equals(TypeOfOrgan.HEART) && !this.heart.empty() && !this.savedHeart
                    && (card.getType().equals(TypeOfOrgan.HEART) || card.getType().equals(TypeOfOrgan.MULTICOLOR) ||
                    this.isVirusMulticolor(this.heart))) {
                return addMedicineIfValid(TypeOfOrgan.HEART, this.heart, medicineCard, discardPile);
            }

            if ((type.equals(TypeOfOrgan.STOMACH) && !this.stomach.empty() && !this.savedStomach) && ((card.getType().equals(TypeOfOrgan.STOMACH)) || (card.getType().equals(TypeOfOrgan.MULTICOLOR)) ||
                    this.isVirusMulticolor(this.stomach))) {
                return addMedicineIfValid(TypeOfOrgan.STOMACH, this.stomach, medicineCard, discardPile);
            }

            if ((type.equals(TypeOfOrgan.BRAIN) && !this.brain.empty() && !this.savedBrain) && ((card.getType().equals(TypeOfOrgan.BRAIN)) || (card.getType().equals(TypeOfOrgan.MULTICOLOR)) ||
                    this.isVirusMulticolor(this.brain))) {
                return addMedicineIfValid(TypeOfOrgan.BRAIN, this.brain, medicineCard, discardPile);
            }

            if ((type.equals(TypeOfOrgan.BONE) && !this.bone.empty() && !this.savedBone) && ((card.getType().equals(TypeOfOrgan.BONE)) || (card.getType().equals(TypeOfOrgan.MULTICOLOR)) ||
                    this.isVirusMulticolor(this.bone))) {
                return addMedicineIfValid(TypeOfOrgan.BONE, this.bone, medicineCard, discardPile);
            }
            if (type.equals(TypeOfOrgan.MULTICOLOR) && !this.multicolor.empty() && !this.savedMulticolor) {
                return addMedicineIfValid(TypeOfOrgan.MULTICOLOR, this.multicolor, medicineCard, discardPile);
            }
        }
        return false;
    }

    public boolean addVirusCard(NormalCard card, TypeOfOrgan type, DiscardPile discardPile) {

        if (card instanceof Virus virusCard) {
            if (type.equals(TypeOfOrgan.HEART) && !this.heart.empty() && !this.savedHeart &&
                    (card.getType().equals(TypeOfOrgan.HEART) || card.getType().equals(TypeOfOrgan.MULTICOLOR) ||
                            this.isMedicineMulticolor(this.heart))) {
                return this.addVirusIfValid(this.heart, virusCard, discardPile);
            }

            if (type.equals(TypeOfOrgan.STOMACH) && !this.stomach.empty() && !this.savedStomach &&
                    (card.getType().equals(TypeOfOrgan.STOMACH) ||
                            card.getType().equals(TypeOfOrgan.MULTICOLOR) || this.isMedicineMulticolor(this.stomach))) {
                return this.addVirusIfValid(this.stomach, virusCard, discardPile);
            }

            if (type.equals(TypeOfOrgan.BRAIN) && !this.brain.empty() && !this.savedBrain &&
                    (card.getType().equals(TypeOfOrgan.BRAIN) || card.getType().equals(TypeOfOrgan.MULTICOLOR) ||
                            this.isVirusMulticolor(this.brain))) {
                return this.addVirusIfValid(this.brain, virusCard, discardPile);
            }

            if (type.equals(TypeOfOrgan.BONE) && !this.bone.empty() && !this.savedBone &&
                    (card.getType().equals(TypeOfOrgan.BONE) || card.getType().equals(TypeOfOrgan.MULTICOLOR) ||
                            this.isVirusMulticolor(this.bone))) {
                return this.addVirusIfValid(this.bone, virusCard, discardPile);
            }
            if (type.equals(TypeOfOrgan.MULTICOLOR) && !this.multicolor.empty() && !this.savedMulticolor) {
                return this.addVirusIfValid(this.multicolor, virusCard, discardPile);
            }
        }
        return false;
    }

    private boolean isVirusMulticolor(Stack<NormalCard> stack) {
        return (stack.peek() instanceof Virus) && (stack.peek().getType().equals(TypeOfOrgan.MULTICOLOR));
    }

    private boolean isMedicineMulticolor(Stack<NormalCard> stack) {
        return (stack.peek() instanceof Medicine) && (stack.peek().getType().equals(TypeOfOrgan.MULTICOLOR));
    }


    // Agrega la carta Medicina en la Pila correspondiente
    private boolean addMedicineIfValid(TypeOfOrgan type, Stack<NormalCard> stack, Medicine card, DiscardPile discardPile) {
        if (stack.peek() instanceof Organ) {
            stack.add(card);
            return true;
        }
        if (stack.peek() instanceof Virus) {
            discardPile.addCard(stack.pop());
            return true;
        }
        if (stack.peek() instanceof Medicine) {
            stack.add(card);
            if (type.equals(TypeOfOrgan.HEART)) {
                this.savedHeart = true;
            }
            if (type.equals(TypeOfOrgan.STOMACH)) {
                this.savedStomach = true;
            }
            if (type.equals(TypeOfOrgan.BRAIN)) {
                this.savedBrain = true;
            }
            if (type.equals(TypeOfOrgan.BONE)) {
                this.savedBone = true;
            }
            if (type.equals(TypeOfOrgan.MULTICOLOR)) {
                this.savedMulticolor = true;
            }
            return true;
        }
        return false;
    }

    // Agrega la carta Virus en la pila correspondiente
    private boolean addVirusIfValid(Stack<NormalCard> stack, Virus card, DiscardPile discardPile) {
        if (!stack.empty()) {
            if (stack.peek() instanceof Organ) {
                stack.add(card);
                return true;
            } else if (stack.peek() instanceof Virus) {
                while(!stack.empty()) {
                    discardPile.addCard(stack.pop());
                }
                discardPile.addCard(card);
                return true;
            } else if (stack.peek() instanceof Medicine) {
                discardPile.addCard(stack.pop());
                discardPile.addCard(card);
                return true;
            }
        }
        return false;
    }

    public boolean playTransplantCard(TypeOfOrgan type, Player player, TypeOfOrgan otherPlayerType) {
        if (this.isNotSaved(type) && player.getBody().isNotSaved(otherPlayerType) && !this.getStackByType(type).empty() && !player.getBody().getStackByType(otherPlayerType).empty()) {
            if (type.equals(otherPlayerType)) { // SI son del mismo type, inercambio
                Stack<NormalCard> aux = this.getStackByType(type);
                this.setStack(player.getBody().getStackByType(otherPlayerType), otherPlayerType);
                player.getBody().setStack(aux, type);
                return true;
            } else if (this.isNotSaved(otherPlayerType) && player.getBody().isNotSaved(type) && this.getStackByType(otherPlayerType).empty() && player.getBody().getStackByType(type).empty()) {
                Stack<NormalCard> aux = this.getStackByType(type);
                this.setStack(player.getBody().getStackByType(otherPlayerType), otherPlayerType);
                player.getBody().setStack(aux, type);

                this.setStack(new Stack<>(), type);
                player.getBody().setStack(new Stack<>(), otherPlayerType);
                return true;
            }
        }
        return false;
    }

    public boolean isNotEmptyOrSaved(TypeOfOrgan type) {
        return !this.getStackByType(type).empty() && this.isNotSaved(type);
    }

    private boolean hasAVirus(TypeOfOrgan type) {
        return this.isNotEmptyOrSaved(type) && (this.getStackByType(type).peek() instanceof Virus);
    }

    private boolean hasOnlyOrgan(TypeOfOrgan type) {
        return this.isNotEmptyOrSaved(type) && (this.getStackByType(type).peek() instanceof Organ);
    }

    private boolean canPlayContagion(TypeOfOrgan virusInOrgan, Player playerToInfect, TypeOfOrgan organToInfect) {
        if (this.hasAVirus(virusInOrgan) && playerToInfect.getBody().hasOnlyOrgan(organToInfect)) {
            return virusInOrgan.equals(TypeOfOrgan.MULTICOLOR) || organToInfect.equals(TypeOfOrgan.MULTICOLOR) ||
                    virusInOrgan.equals(organToInfect);
        }
        return false;
    }

    public boolean playContagionCard(TypeOfOrgan virusInBody, Player playerToInfect, TypeOfOrgan organToInfect, DiscardPile discardPile) {
        if (this.canPlayContagion(virusInBody, playerToInfect, organToInfect)) {
            return playerToInfect.getBody().addVirusCard(this.getStackByType(virusInBody).pop(), organToInfect, discardPile);
        }
        return false;
    }

    public boolean canPlayContagionInBody(TypeOfOrgan virusInOrgan, Player playerToInfect) {
        if (virusInOrgan.equals(TypeOfOrgan.MULTICOLOR)) {
            return this.canPlayContagion(virusInOrgan, playerToInfect, TypeOfOrgan.HEART) ||
                    this.canPlayContagion(virusInOrgan, playerToInfect, TypeOfOrgan.STOMACH) ||
                    this.canPlayContagion(virusInOrgan, playerToInfect, TypeOfOrgan.BRAIN) ||
                    this.canPlayContagion(virusInOrgan, playerToInfect, TypeOfOrgan.BONE) ||
                    this.canPlayContagion(virusInOrgan, playerToInfect, TypeOfOrgan.MULTICOLOR);
        }
        return this.canPlayContagion(virusInOrgan, playerToInfect, virusInOrgan) ||
                this.canPlayContagion(virusInOrgan, playerToInfect, TypeOfOrgan.MULTICOLOR);
    }

    public boolean playOrganThiefCard(Player player, TypeOfOrgan otherPlayerType) {
        if (player.getBody().isNotSaved(otherPlayerType) && !player.getBody().getStackByType(otherPlayerType).empty()) {
            if (this.getStackByType(otherPlayerType).empty()) {
                this.setStack(player.getBody().getStackByType(otherPlayerType), otherPlayerType);
                player.getBody().setStack(new Stack<>(), otherPlayerType);
                return true;
            }
        }
        return false;
    }

    public List<List<String>> getStateMatrix() {
        List<List<String>> matrix = initializeMatrix();

        fillMatrix(matrix, savedHeart, heart, "Corazón", 0);
        fillMatrix(matrix, savedStomach, stomach, "Estomago", 1);
        fillMatrix(matrix, savedBrain, brain, "Cerebro", 2);
        fillMatrix(matrix, savedBone, bone, "Hueso", 3);
        fillMatrix(matrix, savedMulticolor, multicolor, "Multicolor", 4);

        return matrix;
    }

    public void fillMatrix(List<List<String>> matrix, boolean saved, Stack<NormalCard> stack, String organ, int position) {
        if (saved) {
            NormalCard aux = stack.pop();
            matrix.get(0).set(position, cardName(aux));
            matrix.get(1).set(position, cardName(stack.peek()));
            stack.add(aux);
            matrix.get(2).set(position, this.fixTabular(organ));
        } else if (!stack.empty()) {
            if (stack.peek() instanceof Organ) {
                matrix.get(2).set(position, this.fixTabular(organ));
            } else {
                matrix.get(1).set(position, this.cardName(stack.peek()));
                matrix.get(2).set(position, this.fixTabular(organ));
            }
        }
    }

    private String fixTabular(String organ) {
        if (organ.length() < 4) {
            return organ + "\t\t\t\t\t";
        }
        if (organ.length() < 8) {
            return organ + "\t\t\t\t";
        }
        if (organ.length() < 12) {
            return organ + "\t\t\t";
        }
        if (organ.length() < 16) {
            return organ + "\t\t";
        }
        if (organ.length() < 20) {
            return organ + "\t";
        }
        return organ;
    }

    private String cardName(NormalCard card) {
        if (card instanceof Virus) {
            return "V-" + getCardType(card);  //Character.toString(0x1F9A0)
        } else {
            return "M-" + getCardType(card); //Character.toString(0x1F48A)
        }
    }

    private String getCardType(NormalCard card) {
        if (card.getType().equals(TypeOfOrgan.HEART)) {
            return fixTabular("Corazón");
        } else if (card.getType().equals(TypeOfOrgan.STOMACH)) {
            return fixTabular("Estomago");
        } else if (card.getType().equals(TypeOfOrgan.BRAIN)) {
            return fixTabular("Cerebro");
        } else if (card.getType().equals(TypeOfOrgan.BONE)) {
            return fixTabular("Hueso");
        } else {
            return fixTabular("Multicolor");
        }
    }

    private List<List<String>> initializeMatrix() {
        List<List<String>> matrix = new ArrayList<>();
        final String SPACE = "\t\t\t\t";
        for (int i = 0; i < 3; i++) {
            List<String> list = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                list.add(SPACE);
            }
            matrix.add(list);
        }

        List<String> list = new ArrayList<>();

        // Forma de escapear UNICODE ya que no esta disponible en UTF8 mas de 4 caracteres
        list.add("CORAZÓN " + Character.toString(0x1FAC0) + "\t | \t");  // Corazon 0x1FAC0 1F7E5
        list.add("ESTÓMAGO " + Character.toString(0x1F7E9) + "\t | \t"); // Pulmon 0x1FAC1 --> no hay de estomago
        list.add("CEREBRO " + Character.toString(0x1F9E0) + "\t | \t");  // Cerebro 0x1F9E0  1F7E6
        list.add("HUESO " + Character.toString(0x1F9B4) + "\t | \t");    // Hueso 0x1F9B4   1F7E8
        list.add("MULTICOLOR " + Character.toString(0x1F6B6)); // 0x1F464, Arcoiris 1F308

        matrix.add(list);

        return matrix;
    }

}
