import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubdivision } from 'app/shared/model/subdivision.model';
import { SubdivisionService } from './subdivision.service';

@Component({
  selector: 'jhi-subdivision-delete-dialog',
  templateUrl: './subdivision-delete-dialog.component.html'
})
export class SubdivisionDeleteDialogComponent {
  subdivision: ISubdivision;

  constructor(
    protected subdivisionService: SubdivisionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.subdivisionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'subdivisionListModification',
        content: 'Deleted an subdivision'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-subdivision-delete-popup',
  template: ''
})
export class SubdivisionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ subdivision }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SubdivisionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.subdivision = subdivision;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/subdivision', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/subdivision', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
