import {NgModule} from '@angular/core';
import {Routes, RouterModule} from "@angular/router";
import {EmployeeListComponent} from "./component/employee-list/employee-list.component";
import {CreateEmployeeComponent} from "./component/create-employee/create-employee.component";
import {UpdateEmployeeComponent} from "./component/update-employee/update-employee.component";
import {EmployeeDetailsComponent} from "./component/employee-details/employee-details.component";
import {CompanyListComponent} from "./component/company-list/company-list.component";
import {CompanyDetailComponent} from "./component/company-detail/company-detail.component";
import {CreateCompanyComponent} from "./component/create-company/create-company.component";
import {UpdateCompanyComponent} from "./component/update-company/update-company.component";


const routes: Routes = [
  {path: '', redirectTo: 'employee', pathMatch: 'full'},
  {path: 'employees', component: EmployeeListComponent},
  {path: 'add-employee', component: CreateEmployeeComponent},
  {path: 'update-employee/:id', component: UpdateEmployeeComponent},
  {path: 'details-employee/:id', component: EmployeeDetailsComponent},
  {path: 'companies', component: CompanyListComponent},
  {path: 'add-company', component: CreateCompanyComponent},
  {path: 'update-company/:id', component: UpdateCompanyComponent},
  {path: 'details-company/:id', component: CompanyDetailComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
