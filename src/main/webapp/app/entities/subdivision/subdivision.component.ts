import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubdivision } from 'app/shared/model/subdivision.model';
import { SubdivisionService } from './subdivision.service';
import { SubdivisionDeleteDialogComponent } from './subdivision-delete-dialog.component';

@Component({
  selector: 'jhi-subdivision',
  templateUrl: './subdivision.component.html',
})
export class SubdivisionComponent implements OnInit, OnDestroy {
  subdivisions?: ISubdivision[];
  eventSubscriber?: Subscription;

  constructor(
    protected subdivisionService: SubdivisionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.subdivisionService.query().subscribe((res: HttpResponse<ISubdivision[]>) => (this.subdivisions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSubdivisions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISubdivision): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSubdivisions(): void {
    this.eventSubscriber = this.eventManager.subscribe('subdivisionListModification', () => this.loadAll());
  }

  delete(subdivision: ISubdivision): void {
    const modalRef = this.modalService.open(SubdivisionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.subdivision = subdivision;
  }
}
