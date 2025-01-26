import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomePageComponent } from './home-page/home-page.component';
import { EmployeeManagementComponent } from './employee-management/employee-management.component';
import { ProjectDetailsComponent } from './project-details/project-details.component';
import { PurchaseOrderComponent } from './purchase-order/purchase-order.component';



export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'home-page', component: HomePageComponent},
    { path: 'employee-management', component: EmployeeManagementComponent},
    { path: 'project-details', component: ProjectDetailsComponent },
    { path: 'purchase-order', component: PurchaseOrderComponent },
    
];
