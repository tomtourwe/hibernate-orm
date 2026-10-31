package org.hibernate.orm.test.querycache;

/**
 * @author Gavin King
 */
public class Student {
	private long studentNumber;
	private PersonName name;

	public Student() {}

	public Student(long studentNumber, PersonName name) {
		this.studentNumber = studentNumber;
		this.name = name;
	}

	public PersonName getName() {
		return name;
	}

	public void setName(PersonName name) {
		this.name = name;
	}

	public long getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(long studentNumber) {
		this.studentNumber = studentNumber;
	}

	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || ! ( o instanceof Student ) ) {
			return false;
		}

		Student student = ( Student ) o;

		if ( studentNumber != student.getStudentNumber() ) {
			return false;
		}
		if ( name != null ? !name.equals( student.getName() ) : student.getName() != null ) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = ( int ) ( studentNumber ^ ( studentNumber >>> 32 ) );
		result = 31 * result + ( name != null ? name.hashCode() : 0 );
		return result;
	}
}
