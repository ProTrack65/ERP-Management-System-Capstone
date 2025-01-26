import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-project-management',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.css']
})
export class ProjectDetailsComponent {
  projects: any[] = []; // Array to store project details
  availableEmployees: any[] = [
    { id: 1, name: 'John Doe', assigned: false },
    { id: 2, name: 'Jane Smith', assigned: false },
    // Add more employees as needed
  ]; // List of available employees for assignment
  isAddingNewRow: boolean = false; // Toggle new row visibility
  isAssigningEmployees: boolean = false; // Toggle employee assignment modal
  newProject: any = {
    id: '',
    name: '',
    clientId: '',
    clientName: '',
    startDate: '',
    endDate: '',
    budget: '',
    status: ''
  }; // Object to store new project data

  // Function to add a new project row
  addNewRow(): void {
    this.isAddingNewRow = true;
  }

  // Function to save the new project row
  saveNewRow(): void {
    if (
      this.newProject.id &&
      this.newProject.name &&
      this.newProject.clientId &&
      this.newProject.clientName &&
      this.newProject.startDate &&
      this.newProject.endDate &&
      this.newProject.budget &&
      this.newProject.status
    ) {
      this.projects.push({ ...this.newProject });
      this.clearNewProject();
      this.isAddingNewRow = false;
    } else {
      alert('Please fill all fields!');
    }
  }

  // Function to cancel adding a new project row
  cancelNewRow(): void {
    this.isAddingNewRow = false;
    this.clearNewProject();
  }

  // Function to clear the new project object
  clearNewProject(): void {
    this.newProject = {
      id: '',
      name: '',
      clientId: '',
      clientName: '',
      startDate: '',
      endDate: '',
      budget: '',
      status: ''
    };
  }

  // Function to show employee assignment modal
  assignEmployees(projectId: string): void {
    this.isAssigningEmployees = true;
  }

  // Function to close the assignment modal
  closeAssignModal(): void {
    this.isAssigningEmployees = false;
  }

  // Function to save the assigned employees for the project
  saveAssignedEmployees(): void {
    // You can implement logic to save assignments here (e.g., link employees to project)
    alert('Employees assigned successfully!');
    this.closeAssignModal();
  }

  // Function to explore project details
  exploreDetails(projectId: string): void {
    alert('Explore details for project ID: ' + projectId);
  }
}
