package Bank_App;

import Bank_DAO.BankDAO;
import java.util.Scanner;
import Bank_DTO.Bank;

public class BankApp {
	public static void main(String[] args) {
		boolean cond = true;
		while (cond) {
			System.out.println("***** Smart ATM *****");
			System.out.println("-------------------------------");
			System.out.println("Enter 1 for Account creation");
			System.out.println("Enter 2 for Details ");
			System.out.println("Enter 3 for Check balance ");
			System.out.println("Enter 4 for Deposit");
			System.out.println("Enter 6 for PIN change ");
			System.out.println("Enter 7 to Transfer the amount ");
			System.out.println("Enter 8 for Exit");
			System.out.println("----------------");
			System.out.println("SELECT THE OPTION ");

			Scanner sc = new Scanner(System.in);
			int option = sc.nextInt();
			Bank b = new Bank();
			switch (option) {
			case 1:
				sc.nextLine();
				System.out.println("Enter the name : ");
				String name = sc.nextLine();
				System.out.println("Enter the Phone number : ");
				String phone = sc.next();
				System.out.println("Enter the Mail : ");
				String mail = sc.next();
				System.out.println("Enter the initial Deposit : ");
				int bal = sc.nextInt();

				System.out.println("Enter the PIN : ");
				String pin = sc.next();
				System.out.println("Confirm your PIN : ");
				String c = sc.next();
				b.setName(name);
				b.setBalance(bal);
				b.setEmail(mail);
				b.setPhone(phone);
				b.setPin(pin);
				boolean res = BankDAO.createAccount(b);
				if (res) {
					System.out.println("Happy to have " + name + " here");
					System.out.println("Thank you for creating account");
				} else {
					System.out.println("Account creation failed....");
				}
				break;

			case 2:
				System.out.println("Enter the account number : ");
				int acno = sc.nextInt();
				System.out.println("Enter the password : ");
				pin = sc.next();
				b.setAccno(acno);
				b.setPin(pin);
				Bank user = BankDAO.getDetails(b);
				System.out.println("Name : " + user.getName());
				System.out.println("Email : " + user.getEmail());
				System.out.println("Phone : " + user.getPhone());
				System.out.println("Balance : " + user.getBalance());
				break;

			case 3:
				System.out.println("Enter the account number : ");
				acno = sc.nextInt();
				System.out.println("Enter the password : ");
				pin = sc.next();
				b.setAccno(acno);
				b.setPin(pin);
				user = BankDAO.getDetails(b);
				System.out.println("Balance = " + user.getBalance());
				break;

			case 4:
				System.out.println("Enter the account number : ");
				acno = sc.nextInt();
				System.out.println("Enter the amount to deposit : ");
				bal = sc.nextInt();
				if (bal > 0) {
					int oldBalance = BankDAO.getBal(acno);
					int newBalance = oldBalance + bal;
					int upbal = BankDAO.updateBal(acno, newBalance);
					if (upbal > 0) {
						System.out.println("Deposit successfully...");
					} else {
						System.out.println("Deposit Failed..");
					}
				} else {
					System.out.println("Deposit value is greater than 0");
				}
				break;
			case 5:
				System.out.println("Enter the account number : ");
				acno = sc.nextInt();
				System.out.println("Enter the password : ");
				pin = sc.next();
				b.setAccno(acno);
				b.setPin(pin);
				user = BankDAO.getDetails(b);
				if (user != null) {
					System.out.println("Enter the withdrawal amount : ");
					int withamt = sc.nextInt();
					if (withamt <= user.getBalance()) {
						int upbal = user.getBalance() - withamt;
						int a = BankDAO.updateBal(acno, upbal);

						if (a > 0) {
							System.out.println("Withdraw success of amount ");
						}
					} else {
						System.out.println("Insufficient fund....");
					}
				} else {
					System.out.println("No such account found...");
				}
				break;

			case 6:
				System.out.println("Enter the account number : ");
				acno = sc.nextInt();
				System.out.println("Enter the Current pin ");
				pin = sc.next();
				b.setAccno(acno);
				b.setPin(pin);
				user = BankDAO.getDetails(b);
				if (user != null) {
					System.out.println("Enter the new pin : ");
					String npin = sc.next();
					int up = BankDAO.updatePin(b.getAccno(), npin);
					if (up > 0) {
						System.out.println("Updation success...");
					} else {
						System.out.println("Updation failed..");
					}
				} else {
					System.out.println("No such account found...");
				}
				break;
			case 7:
				System.out.println("Enter the benificiary account number ");
				int bAcno = sc.nextInt();
				System.out.println("Enter the amount tp transfer :");
				int tAmt = sc.nextInt();
				System.out.println("Enter the your account number : ");
				acno = sc.nextInt();
				System.out.println("Enter the pin ");
				pin = sc.next();
				b.setAccno(acno);
				b.setPin(pin);
				Bank beUser = BankDAO.getBenficiaryAccount(bAcno);
				user = BankDAO.getDetails(b);
				int tran = 0;

				if (user != null) {
					if (beUser != null) {
						if (user.getBalance() >= tAmt) {
							int bbal = beUser.getBalance() + tAmt;
							int ubal = user.getBalance() - tAmt;

							tran = BankDAO.updateBal(bAcno, bbal);
							BankDAO.updateBal(acno, ubal);
							if (tran > 0) {
								System.out.println("Transaction success... for : " + beUser.getName());
							} else {
								System.out.println("Transaction failes...");
							}
						} else {
							System.out.println("Insufficient balance....");
						}
					} else {
						System.out.println("No such Benificiery account ");
					}
				} else {
					System.out.println("No such account found");
				}
				break;

			case 8:
				cond = false;
				System.out.println("Thank You Visit Again...");
				break;

			default:
				System.out.println("No such option found....");
			}
			System.out.println("-------------------------------------------------");

		}
	}
}
