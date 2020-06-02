import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {CompanyService} from "../../model/company.service";

@Component({
  selector: 'app-company-list',
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.css']
})
export class CompanyListComponent implements OnInit {

  companies: any;

  constructor(private companyService: CompanyService, private router: Router) {
  }

  ngOnInit(): void {
    this.reloadData();
  }

  private reloadData() {
    this.companyService.getCompaniesList().subscribe(data=>{
      this.companies = data;
    });
  }

  companyDetails(id: number) {
    this.router.navigate(['details-company', id]);
  }

  updateCompany(id: number) {
    this.router.navigate(['update-company', id]);
  }

  deleteCompany(id: number) {
  this.companyService.deleteCompany(id).subscribe(data=>{
    console.log(data);
    this.reloadData();
  },error => console.log(error))
  }
}
