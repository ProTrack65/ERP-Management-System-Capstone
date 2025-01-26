import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms'; // Import FormsModule
import { CommonModule } from '@angular/common'; // Import CommonModule

@Component({
  selector: 'app-employee-management',
  standalone: true, // Ensure standalone is true
  imports: [FormsModule, CommonModule], // Add FormsModule and CommonModule to imports
  templateUrl: './employee-management.component.html',
  styleUrls: ['./employee-management.component.css']
})
export class EmployeeManagementComponent {
  employees: any[] = []; // Array to store employee details
  isAddingNewRow: boolean = false; // Toggle new row visibility
  newEmployee: any = {
    id: '',
    name: '',
    designation: '',
    status: '',
    projectId: '',
    salary: '',
    joinDate: '',
    bankDetails: ''
  }; // Object to store new employee data

  // Function to add a new row
  addNewRow(): void {
    this.isAddingNewRow = true; // Show the new row
  }

  // Function to save the new row
  saveNewRow(): void {
    if (
      this.newEmployee.id &&
      this.newEmployee.name &&
      this.newEmployee.designation &&
      this.newEmployee.status &&
      this.newEmployee.projectId &&
      this.newEmployee.salary &&
      this.newEmployee.joinDate &&
      this.newEmployee.bankDetails
    ) {
      this.employees.push({ ...this.newEmployee }); // Add new employee to the table
      this.clearNewEmployee(); // Clear the new employee object
      this.isAddingNewRow = false; // Hide the new row
    } else {
      alert('Please fill all fields!');
    }
  }

  // Function to cancel adding a new row
  cancelNewRow(): void {
    this.isAddingNewRow = false; // Hide the new row
    this.clearNewEmployee(); // Clear the form
  }

  // Function to clear the new employee object
  clearNewEmployee(): void {
    this.newEmployee = {
      id: '',
      name: '',
      designation: '',
      status: '',
      projectId: '',
      salary: '',
      joinDate: '',
      bankDetails: ''
    };
  }

  // Function to edit a row
  editRow(index: number): void {
    const employee = this.employees[index];
    this.newEmployee = { ...employee }; // Copy employee data to the newEmployee object
    this.employees.splice(index, 1); // Remove the employee from the table
    this.isAddingNewRow = true; // Show the editable row
  }

  // Function to delete a row
  deleteRow(index: number): void {
    this.employees.splice(index, 1);
  }
}
