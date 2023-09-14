class Test {
	static void main() {
		System s = new System();
		Another a = new Another();
		a.m1();
	}
}

class Another {
	dynamic int m1() {
		return 10;
	}
	
	dynamic char m2() {
		return 'a';
	}
}