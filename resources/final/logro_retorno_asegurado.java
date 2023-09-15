class Test {
	static void main() {
		
	}
	
	dynamic void voidWithoutReturn() {
		int x = 0;
	}
	
	dynamic void voidWithReturn() {
		int x = 0;
		return;
	}
	
	dynamic int withReturnInMainBlock() {
		int x = 0;
		return x;
	}
	
	dynamic int withReturnInIfAndElse() {
		if (0 < 1) {
			return 0;
		} else
			return 1;
	}
	
	dynamic int withReturnInChildBlock() {
		{
			{
				{}
			}
			{
				if (true) return 0;
				else return 1;
			}
		}
		int x = 0;
	}
	
	// dynamic int withReturnInIfOnly() {
		// if (0 == 0) {
			// return 0;
		// }
	// }
	
	// dynamic int withReturnInElseOnly() {
		// if (0 == 0) {
			// int x = 0;
		// } else {
			// return 0;
		// }
	// }
}
