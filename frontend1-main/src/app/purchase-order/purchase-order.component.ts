import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-purchase-order',
  imports: [FormsModule,CommonModule],
  templateUrl: './purchase-order.component.html',
  styleUrls: ['./purchase-order.component.css']
})
export class PurchaseOrderComponent {
  searchQuery: string = '';
  expandedPOIds: number[] = [];  // Track which POs are expanded
  pOs = [
    { id: 1, name: 'PO #1234', description: 'This is a sample PO description for project 1', additionalInfo: 'Additional details for PO #1234' },
    { id: 2, name: 'PO #5678', description: 'Another PO for project 2', additionalInfo: 'Additional details for PO #5678' },
    { id: 3, name: 'PO #9101', description: 'Details for PO in project 3', additionalInfo: 'Additional details for PO #9101' },
    // Add more POs as necessary
  ];

  // Filter POs based on the search query
  get filteredPOs() {
    return this.pOs.filter(po => po.name.toLowerCase().includes(this.searchQuery.toLowerCase()));
  }

  // Check if the details of a PO are visible
  isDetailsVisible(poId: number): boolean {
    return this.expandedPOIds.includes(poId);
  }

  // Toggle the visibility of the PO details
  togglePODetails(poId: number) {
    const index = this.expandedPOIds.indexOf(poId);
    if (index === -1) {
      // If PO details are not visible, add the PO ID to the array
      this.expandedPOIds.push(poId);
    } else {
      // If PO details are already visible, remove the PO ID from the array
      this.expandedPOIds.splice(index, 1);
    }
  }

  // Handle the search input dynamically (you can add debouncing if needed)
  onSearch() {
    // This function gets triggered whenever the user types in the search bar
  }
}
