import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISubdivision } from 'app/shared/model/subdivision.model';
import { AccountService } from 'app/core';
import { SubdivisionService } from './subdivision.service';

@Component({
  selector: 'jhi-subdivision',
  templateUrl: './subdivision.component.html'
})
export class SubdivisionComponent implements OnInit, OnDestroy {
  subdivisions: ISubdivision[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected subdivisionService: SubdivisionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.subdivisionService
      .query()
      .pipe(
        filter((res: HttpResponse<ISubdivision[]>) => res.ok),
        map((res: HttpResponse<ISubdivision[]>) => res.body)
      )
      .subscribe(
        (res: ISubdivision[]) => {
          this.subdivisions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSubdivisions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISubdivision) {
    return item.id;
  }

  registerChangeInSubdivisions() {
    this.eventSubscriber = this.eventManager.subscribe('subdivisionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
