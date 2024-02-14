/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package chesscore;

/**
 *
 * @author ADMIN
 */
public enum Color {
    WHITE("W"), BLACK("B");

	private String value;

	Color(String value) {
		this.value = value;
	}
         
	@Override
	public String toString() {
		return value;
	}
    
}
