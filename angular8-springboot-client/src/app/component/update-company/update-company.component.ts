import {Component, OnInit} from '@angular/core';
import {Company} from "../../model/company";
import {CompanyService} from "../../model/company.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-update-company',
  templateUrl: './update-company.component.html',
  styleUrls: ['./update-company.component.css']
})
export class UpdateCompanyComponent implements OnInit {
  submitted: any;
  company: Company;
  id: number;

  constructor(private companyService: CompanyService, private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.company = new  Company();
    this.id = this.activatedRoute.snapshot.params['id'];

    this.companyService.getCompany(this.id).subscribe(data=>{
      this.company = data;
    },error => console.log(error))

  }

  updateCompany() {
    this.companyService.updateCompany(this.id, this.company).subscribe(data=>{
      this.gotoList();
    },error => console.log(error));
    this.company = new Company();
  }

  onSubmit() {
    this.updateCompany();
  }

  gotoList() {
    this.router.navigate(['/companies']);
  }
}
