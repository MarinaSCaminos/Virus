package com.virus.model;

import com.virus.model.card.Card;
import com.virus.model.card.NormalCard;
import com.virus.model.card.Organ;
import com.virus.model.card.Medicine;
import com.virus.model.card.Virus;
import com.virus.model.enums.TypeOfOrgan;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
                                                // TODO crear Stack multicolor y cambiar funciones respectivamente
public class Body {                             // medicine \u1F48A , virus \u1F9A0

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

    public boolean isSavedHeart() {
        return savedHeart;
    }

    public boolean isSavedStomach() {
        return savedStomach;
    }

    public boolean isSavedBrain() {
        return savedBrain;
    }

    public boolean isSavedBone() {
        return savedBone;
    }

    public boolean isSavedMulticolor() { return savedMulticolor; }    // TODO buscar donde se usaria

    public Body(){
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

    private boolean isSaved(TypeOfOrgan type) {
        if(type.equals(TypeOfOrgan.HEART)) {
            return this.savedHeart;
        }else if(type.equals(TypeOfOrgan.STOMACH)) {
            return this.savedStomach;
        }else if(type.equals(TypeOfOrgan.BRAIN)) {
            return this.savedBrain;
        }else if(type.equals(TypeOfOrgan.BONE)) {
            return this.savedBone;
        }else{
            return this.savedMulticolor;
        }
    }

    private void setStack(Stack<NormalCard> stack) {
        if(stack.equals(heart)) {
            this.setHeart(stack);
        }else if(stack.equals(stomach)) {
            this.setStomach(stack);
        }else if(stack.equals(brain)) {
            this.setBrain(stack);
        }else if(stack.equals(bone)) {
            this.setBone(stack);
        }else{
            this.setMulticolor(stack);
        }
    }

    private Stack<NormalCard> getStackByType(TypeOfOrgan type) {
        if(type.equals(TypeOfOrgan.HEART)) {
            return this.heart;
        }else if(type.equals(TypeOfOrgan.STOMACH)) {
            return this.stomach;
        }else if(type.equals(TypeOfOrgan.BRAIN)) {
            return this.brain;
        }else if(type.equals(TypeOfOrgan.BONE)) {
            return this.bone;
        }else{
            return this.multicolor;
        }
    }


    public boolean addOrgan(Organ organ) {
        if(organ.getType().equals(TypeOfOrgan.HEART)) {
            return addOrganIfValid(savedHeart, heart, organ);
        }
        if(organ.getType().equals(TypeOfOrgan.STOMACH)) {
            return addOrganIfValid(savedStomach, stomach, organ);
        }
        if(organ.getType().equals(TypeOfOrgan.BRAIN)) {
            return addOrganIfValid(savedBrain, brain, organ);
        }
        if(organ.getType().equals(TypeOfOrgan.BONE)) {
            return addOrganIfValid(savedBone, bone, organ);
        }
        if(organ.getType().equals(TypeOfOrgan.MULTICOLOR)) {
            return addOrganIfValid(savedMulticolor , multicolor, organ);
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

    // TODO Separar en dos funciones
    // Vefirica si la accion pedida es posible (Agregar una carta en un typo de pila)
    public boolean addCard(NormalCard card, TypeOfOrgan type) {

        if(card instanceof Medicine) {
            Medicine medicineCard = (Medicine) card;
            // Si el tipo de stack es multicolor o es igual al tipo de carta
            if(type.equals(TypeOfOrgan.MULTICOLOR) || type.equals(card.getType()) || card.getType().equals(TypeOfOrgan.MULTICOLOR)) {
                return addMedicine(medicineCard, type);
            }else{
                // Si el tipo de stack no es muticolor o del mismo tipo de la carta, entonces me fijo que se encuntra en el tope
                if(type.equals(TypeOfOrgan.HEART) && !this.heart.empty() && !this.savedHeart) {
                    if( (this.heart.peek() instanceof Virus) && (this.heart.peek().getType().equals(TypeOfOrgan.MULTICOLOR))) {
                        return addMedicine(medicineCard, type);
                    }
                }
                if(type.equals(TypeOfOrgan.STOMACH) && !this.stomach.empty() && !this.savedStomach) {
                    if( (this.stomach.peek() instanceof Virus) && (this.stomach.peek().getType().equals(TypeOfOrgan.MULTICOLOR))) {
                        return addMedicine(medicineCard, type);
                    }
                }
                if(type.equals(TypeOfOrgan.BRAIN) && !this.brain.empty() && !this.savedBrain) {
                    if( (this.brain.peek() instanceof Virus) && (this.brain.peek().getType().equals(TypeOfOrgan.MULTICOLOR))) {
                        return addMedicine(medicineCard, type);
                    }
                }
                if(type.equals(TypeOfOrgan.BONE) && !this.bone.empty() && !this.savedBone) {
                    if( (this.bone.peek() instanceof Virus) && (this.bone.peek().getType().equals(TypeOfOrgan.MULTICOLOR))) {
                        return addMedicine(medicineCard, type);
                    }
                }
            }
        }

        if(card instanceof Virus) {
            Virus virusCard = (Virus) card;
            if(type.equals(TypeOfOrgan.MULTICOLOR) || type.equals(card.getType()) || card.getType().equals(TypeOfOrgan.MULTICOLOR)) {
                return addVirus(virusCard, type);
            }else{
                // Si el tipo de stack no es muticolor o del mismo tipo de la carta, entonces me fijo que se encuntra en el tope
                if(type.equals(TypeOfOrgan.HEART) && !this.heart.empty() && !this.savedHeart) {
                    if( (this.heart.peek() instanceof Virus) && (this.heart.peek().getType().equals(TypeOfOrgan.MULTICOLOR))) {
                        return addVirus(virusCard, type);
                    }
                }
                if(type.equals(TypeOfOrgan.STOMACH) && !this.stomach.empty() && !this.savedStomach) {
                    if( (this.stomach.peek() instanceof Virus) && (this.stomach.peek().getType().equals(TypeOfOrgan.MULTICOLOR))) {
                        return addVirus(virusCard, type);
                    }
                }
                if(type.equals(TypeOfOrgan.BRAIN) && !this.brain.empty() && !this.savedBrain) {
                    if( (this.brain.peek() instanceof Virus) && (this.brain.peek().getType().equals(TypeOfOrgan.MULTICOLOR))) {
                        return addVirus(virusCard, type);
                    }
                }
                if(type.equals(TypeOfOrgan.BONE) && !this.bone.empty() && !this.savedBone) {
                    if( (this.bone.peek() instanceof Virus) && (this.bone.peek().getType().equals(TypeOfOrgan.MULTICOLOR))) {
                        return addVirus(virusCard, type);
                    }
                }
            }
        }
        return false;
    }

    //
    private boolean addMedicine(Medicine card, TypeOfOrgan type) {
        if(type.equals(TypeOfOrgan.HEART)) {
            return addMedicineIfValid(this.savedHeart, TypeOfOrgan.HEART, this.heart, card);
        }
        if(type.equals(TypeOfOrgan.STOMACH)) {
            return addMedicineIfValid(this.savedStomach, TypeOfOrgan.STOMACH, this.stomach, card);
        }
        if(type.equals(TypeOfOrgan.BRAIN)) {
            return addMedicineIfValid(this.savedBrain, TypeOfOrgan.BRAIN, this.brain, card);
        }
        if(type.equals(TypeOfOrgan.BONE)) {
            return addMedicineIfValid(this.savedBone, TypeOfOrgan.BONE, this.bone, card);
        }
        if(type.equals(TypeOfOrgan.MULTICOLOR)) {
            return addMedicineIfValid(this.savedMulticolor, TypeOfOrgan.MULTICOLOR, this.multicolor, card);
        }
        return false;
    }

    // Se fija a que typo pertenece
    private boolean addVirus(Virus card, TypeOfOrgan type) {
        if(type.equals(TypeOfOrgan.HEART)) {
            return this.addVirusIfValid(this.savedHeart, this.heart, card);
        }
        if(type.equals(TypeOfOrgan.STOMACH)) {
            return this.addVirusIfValid(this.savedStomach, this.stomach, card);
        }
        if(type.equals(TypeOfOrgan.BRAIN)) {
            return this.addVirusIfValid(this.savedBrain, this.brain, card);
        }
        if(type.equals(TypeOfOrgan.BONE)) {
            return this.addVirusIfValid(this.savedBone, this.bone, card);
        }
        if(type.equals(TypeOfOrgan.MULTICOLOR)) {
            return this.addVirusIfValid(this.savedMulticolor, this.multicolor, card);
        }
        return false;
    }

    // Agrega la carta Medicina en la Pila correspondiente
    private boolean addMedicineIfValid(boolean saved, TypeOfOrgan type, Stack<NormalCard> stack, Medicine card) {
        if(!saved) {
            if(!stack.empty()) {
                if (stack.peek() instanceof Organ) {
                    stack.add(card);
                    return true;
                }
                if (stack.peek() instanceof Virus) {
                    stack.pop();
                    return true;
                }
                if (stack.peek() instanceof Medicine) {
                    stack.add(card);
                    if(type.equals(TypeOfOrgan.HEART)) {
                        this.savedHeart = true;
                    }
                    if(type.equals(TypeOfOrgan.STOMACH)) {
                        this.savedStomach = true;
                    }
                    if(type.equals(TypeOfOrgan.BRAIN)) {
                        this.savedBrain = true;
                    }
                    if(type.equals(TypeOfOrgan.BONE)) {
                        this.savedBone = true;
                    }
                    if(type.equals(TypeOfOrgan.MULTICOLOR)) {  // Agregue satck multicolor
                        this.savedMulticolor = true;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    // Agrega la carta Virus en la pila correspondiente
    private boolean addVirusIfValid(boolean saved, Stack<NormalCard> stack, Virus card) {
        if(!saved) {
            if(!stack.empty()) {
                if (stack.peek() instanceof Organ) {
                    stack.add(card);
                    return true;
                } else if (stack.peek() instanceof Virus) {
                    // TODO Observer -- mandar a discardPile la pila
                    stack.clear();
                    return true;
                } else if (stack.peek() instanceof Medicine) {
                    stack.pop();
                    return true;
                }
            }
        }
        return false;
    }

    // TODO fijarme si el que obtengo no esta salvado
    public boolean playTransplantCard(TypeOfOrgan type, Player player, TypeOfOrgan otherPlayerType) {
        // Si la stack mia y la del otro jugador no estan salvadas
        if(!this.isSaved(type) && !player.getBody().isSaved(otherPlayerType) && !this.getStackByType(type).empty() && !player.getBody().getStackByType(otherPlayerType).empty()) {
            if(type.equals(otherPlayerType)) { // SI son del mismo type, inercambio
                Stack<NormalCard> aux = this.getStackByType(type) ;
                this.setStack(player.getBody().getStackByType(otherPlayerType));
                player.getBody().setStack(aux);
            }else if(!this.isSaved(otherPlayerType) && !player.getBody().isSaved(type) && !this.getStackByType(otherPlayerType).empty() && !player.getBody().getStackByType(type).empty()) {
                Stack<NormalCard> aux = this.getStackByType(type) ;
                this.setStack(player.getBody().getStackByType(otherPlayerType));
                player.getBody().setStack(aux);

                this.getStackByType(type).clear();
                player.getBody().getStackByType(otherPlayerType).clear();;
            }
        }
        return false;
    }

    public boolean playOrganThiefCard(Player player, TypeOfOrgan otherPlayerType) {
        // Si la stack mia y la del otro jugador no estan salvadas
        if (!player.getBody().isSaved(otherPlayerType) && !player.getBody().getStackByType(otherPlayerType).empty()) {
            if (this.getStackByType(otherPlayerType).empty()) { // Si el stack robado estaba vacio en mi cuerpo
                this.setStack(player.getBody().getStackByType(otherPlayerType));
                player.getBody().getStackByType(otherPlayerType).clear();
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

    public void fillMatrix(List<List<String>> matrix, boolean saved,Stack<NormalCard> stack, String organ, int position) {
        if(saved) {
            matrix.get(0).set(position, "M");
            matrix.get(1).set(position, "M");
            matrix.get(2).set(position, organ);
        }else if(!stack.empty()) {
            if(stack.peek() instanceof Virus) {
                matrix.get(1).set(position, "V");
                matrix.get(2).set(position, organ);
            }else if(stack.peek() instanceof Medicine) {
                matrix.get(1).set(position, "M");
                matrix.get(2).set(position, organ);
            }else{
                matrix.get(2).set(position, organ);
            }
        }
    }

    // TODO optimizar usando char o enum
    private List<List<String>> initializeMatrix() {
        List<List<String>> matrix = new ArrayList<>();
        final String SPACE = " ";
        for(int i=0; i<4; i++) {                        // <3
            List<String> list = new ArrayList<>();
            for(int j=0; j<4; j++) {
                list.add(SPACE);
            }
            matrix.add(list);
        }

        List<String> list = new ArrayList<>();

        // Forma de escapear UNICODE ya que no esta disponible en UTF8 mas de 4 caracteres
        list.add("CORAZÓN" + Character.toString(0x2764));  // Corazon 0x1FAC0
        list.add("ESTÓMAGO" + Character.toString(0x1F49A)); // Pulmon 0x1FAC1 --> no hay de estomago
        list.add("CEREBRO" + Character.toString(0x1F499));  // Cerebro 0x1F9E0
        list.add("HUESO" + Character.toString(0x1F49B));    // Hueso 0x1F9B4
        list.add("MULTICOLOR" + Character.toString(0x1F90D)); // 0x1F464, Arcoiris 1F308

        matrix.add(list);

        return matrix;
    }

}
