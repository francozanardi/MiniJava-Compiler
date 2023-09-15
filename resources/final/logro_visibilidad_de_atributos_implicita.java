class Test {
	static void main() {
		System system = new System();
		Another another = new Another();
		system.printIln(another.publicAttr1);
		system.printIln(another.publicAttr2);
		system.printSln(another.publicAttr3);
		system.printBln(another.publicAttr4 == null);
		system.printBln(another.publicAttr5 == null);
		system.printBln(another.publicAttr6 == null);
		
		// system.printCln(another.privateAttr1);
		// system.printBln(another.privateAttr2 == null);
		// system.printBln(another.privateAttr3 == null);
	}
}

class Another {
	public int publicAttr1, publicAttr2;
	private char privateAttr1;
	String publicAttr3;
	Another publicAttr4;
	Test publicAttr5, publicAttr6;
	private Another privateAttr2, privateAttr3;
	
	Another() {
		publicAttr1 = 1;
		this.publicAttr2 = 2;
		privateAttr1 = '3';
		this.publicAttr3 = "4";
		publicAttr4 = null;
		this.publicAttr5 = new Test();
		publicAttr6 = publicAttr5;
		this.privateAttr2 = this.publicAttr4;
		privateAttr3 = null;
	}
}