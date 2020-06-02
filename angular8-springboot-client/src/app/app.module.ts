import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CreateEmployeeComponent } from './component/create-employee/create-employee.component';
import { EmployeeDetailsComponent } from './component/employee-details/employee-details.component';
import { EmployeeListComponent } from './component/employee-list/employee-list.component';
import { UpdateEmployeeComponent } from './component/update-employee/update-employee.component';
import { UpdateCompanyComponent } from './component/update-company/update-company.component';
import { CompanyDetailComponent } from './component/company-detail/company-detail.component';
import { CompanyListComponent } from './component/company-list/company-list.component';
import { CreateCompanyComponent } from './component/create-company/create-company.component';

@NgModule({
  declarations: [
    AppComponent,
    CreateEmployeeComponent,
    EmployeeDetailsComponent,
    EmployeeListComponent,
    UpdateEmployeeComponent,
    UpdateCompanyComponent,
    CompanyDetailComponent,
    CompanyListComponent,
    CreateCompanyComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
