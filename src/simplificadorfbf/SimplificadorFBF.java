/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplificadorfbf;

import java.util.*;

/**
 *
 * @author Johnguisao
 */
public class SimplificadorFBF {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Stack<String> pilaOperandos = new Stack<String>();
        Stack<String> pilaOperadores = new Stack<String>();
        String expresion = "((q?r)$((p$t))$(o?n))";
        String expresionAux = "";
        for (int i = 0; i < expresion.length(); i++) {
            char cExpresion = expresion.charAt(i);
            switch (cExpresion) {
                case '(':
                    break;
                case '¬'://Operador Negación.
                    pilaOperadores.push(String.valueOf(cExpresion));
                    break;
                case '?'://Operador And
                    pilaOperadores.push(String.valueOf(cExpresion));
                    break;
                case '$'://Operador Or
                    pilaOperadores.push(String.valueOf(cExpresion));
                    break;
                case '%'://Operador condicional
                    pilaOperadores.push(String.valueOf(cExpresion));
                    break;
                case '#'://Operador bicondicional
                    pilaOperadores.push(String.valueOf(cExpresion));
                    break;
                case ')':
                    if (!pilaOperadores.isEmpty()) {
                        expresionAux = "";
                        if (pilaOperandos.peek().length() > 1) {
                            expresionAux += "(" + pilaOperandos.pop() + ")";
                        } else {
                            expresionAux += pilaOperandos.pop();
                        }
                        expresionAux += pilaOperadores.pop();
                        if (pilaOperandos.peek().length() > 1) {
                            expresionAux += "(" + pilaOperandos.pop() + ")";
                        } else {
                            expresionAux += pilaOperandos.pop();
                        }
                        //System.out.print(expresionAux + "\n");
                        pilaOperandos.add(expresionAux);
                    }
                    break;
                default://Cualquier letra representando una proposición.
                    pilaOperandos.add(String.valueOf(cExpresion));
                    break;
            }
        }
        while (pilaOperandos.size() > 1) {
            expresionAux = "";
            if (pilaOperandos.peek().length() > 1) {
                expresionAux += "(" + pilaOperandos.pop() + ")";
            } else {
                expresionAux += pilaOperandos.pop();
            }
            expresionAux += pilaOperadores.pop();
            if (pilaOperandos.peek().length() > 1) {
                expresionAux += "(" + pilaOperandos.pop() + ")";
            } else {
                expresionAux += pilaOperandos.pop();
            }
            expresionAux = simplificarPorPrioridad(expresionAux);
            //System.out.print(expresionAux + "\n");
            pilaOperandos.add(expresionAux);
        }
        String expresionAuxInv = pilaOperandos.pop() + "\n";
        String expresionFinal = new StringBuilder(expresionAuxInv).reverse().toString();
        char[] arrayExpresionFinal = expresionFinal.toCharArray();
        for (int i = 0; i < expresionFinal.length(); i++) {
            if (expresionFinal.charAt(i) == '(') {
                arrayExpresionFinal[i] = ')';
            } else if (expresionFinal.charAt(i) == ')') {
                arrayExpresionFinal[i] = '(';
            }
        }
        expresionFinal = new String(arrayExpresionFinal);
        expresionFinal += "\n";
        System.out.print(expresionFinal);
        
    }
    public static int encontrarPrioridad(char operadorAux) {
        int prioridadOperadorAux = 0;
        switch (operadorAux) {
            case '¬'://Operador Negación.
                prioridadOperadorAux = 4;
                break;
            case '?'://Operador And
                prioridadOperadorAux = 3;
                break;
            case '$'://Operador Or
                prioridadOperadorAux = 3;
                break;
            case '%'://Operador condicional
                prioridadOperadorAux = 2;
                break;
            case '#'://Operador bicondicional
                prioridadOperadorAux = 1;
                break;
            default:
                prioridadOperadorAux = 0;
                break;
        }
        return prioridadOperadorAux;
    }    
    public static String simplificarPorPrioridad(String fbf){
        int prioridadPrimerOperador = 0;
        LinkedList<String> listaOperadores = encontrarOperadores(fbf);
        String primerOperador = listaOperadores.pop();
        prioridadPrimerOperador = encontrarPrioridad(primerOperador.charAt(0));
        Iterator iterator = listaOperadores.iterator();
        boolean prioridadDiferente = false;
        while (iterator.hasNext()) {
            String operadorAux = (String)iterator.next();
            System.out.print("operador aux es:   " + operadorAux + "\n");
            if(encontrarPrioridad(operadorAux.charAt(0)) != prioridadPrimerOperador){
                prioridadDiferente = true;
            }
        }
        if(!prioridadDiferente){
            fbf = fbf.replace("(","");
            fbf = fbf.replace(")","");
        }
        return fbf;
        
    }
    public static LinkedList<String> encontrarOperadores(String fbf){
        LinkedList<String> listaOperadores = new LinkedList<String>();
        for(int i = 0; i < fbf.length(); i++){
            char caracter = fbf.charAt(i);
            switch(caracter){
                case '¬'://Operador Negación.
                listaOperadores.add(String.valueOf(caracter));
                break;
            case '?'://Operador And
                listaOperadores.add(String.valueOf(caracter));
                break;
            case '$'://Operador Or
                listaOperadores.add(String.valueOf(caracter));
                break;
            case '%'://Operador condicional
                listaOperadores.add(String.valueOf(caracter));
                break;
            case '#'://Operador bicondicional
                listaOperadores.add(String.valueOf(caracter));
                break;
            }
        }
        Iterator iterator = listaOperadores.iterator();
        while(iterator.hasNext()){
            String operadorAux = (String)iterator.next();
            System.out.print("operador aux es:   " + operadorAux + "\n");
        }
        return listaOperadores;
    }
}
