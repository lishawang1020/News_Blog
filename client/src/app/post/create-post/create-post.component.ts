import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { SubblogModel } from 'src/app/subblog/subblog-response';
import { Router } from '@angular/router';
import { PostService } from 'src/app/shared/post.service';
import { SubblogService } from 'src/app/subblog/subblog.service';
import { CreatePostPayload } from './create-post.payload';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  createPostForm: FormGroup;
  postPayload: CreatePostPayload;
  subblogs: Array<SubblogModel>;

  constructor(private router: Router, private postService: PostService,
    private subblogService: SubblogService) {
    this.postPayload = {
      postName: '',
      url: '',
      description: '',
      subblogName: ''
    }
  }

  ngOnInit() {
    this.createPostForm = new FormGroup({
      postName: new FormControl('', Validators.required),
      subblogName: new FormControl('', Validators.required),
      url: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
    });
    this.subblogService.getAllSubblogs().subscribe((data) => {
      this.subblogs = data;
    }, error => {
      throwError(error);
    });
  }

  createPost() {
    this.postPayload.postName = this.createPostForm.get('postName').value;
    this.postPayload.subblogName = this.createPostForm.get('subblogName').value;
    this.postPayload.url = this.createPostForm.get('url').value;
    this.postPayload.description = this.createPostForm.get('description').value;

    this.postService.createPost(this.postPayload).subscribe((data) => {
      this.router.navigateByUrl('/');
    }, error => {
      throwError(error);
    })
  }

  discardPost() {
    this.router.navigateByUrl('/');
  }
}