import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { SubblogModel } from '../subblog-response';
import { Router } from '@angular/router';
import { SubblogService } from '../subblog.service';

@Component({
  selector: 'app-create-subblog',
  templateUrl: './create-subblog.component.html',
  styleUrls: ['./create-subblog.component.css']
})
export class CreateSubblogComponent implements OnInit {
  createSubblogForm: FormGroup;
  subblogModel: SubblogModel;
  title = new FormControl('');
  description = new FormControl('');

  constructor(private router: Router, private subblogService: SubblogService) {
    this.createSubblogForm = new FormGroup({
      title: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    });
    this.subblogModel = {
      name: '',
      description: ''
    }
  }

  ngOnInit(): void {
  }

  discard() {
    this.router.navigateByUrl('/');
  }

  createSubblog() {
    this.subblogModel.name = this.createSubblogForm.get('title').value;
    this.subblogModel.description = this.createSubblogForm.get('description').value;
    this.subblogModel.numberOfPosts = 0;
    this.subblogService.createSubblog(this.subblogModel).subscribe(() => {
      this.router.navigateByUrl('/list-subblogs');
    }, () => {
      console.log('Error occurred');
    })
  }
}