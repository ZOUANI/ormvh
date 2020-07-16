import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { LeServiceUpdateComponent } from 'app/entities/le-service/le-service-update.component';
import { LeServiceService } from 'app/entities/le-service/le-service.service';
import { LeService } from 'app/shared/model/le-service.model';

describe('Component Tests', () => {
  describe('LeService Management Update Component', () => {
    let comp: LeServiceUpdateComponent;
    let fixture: ComponentFixture<LeServiceUpdateComponent>;
    let service: LeServiceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [LeServiceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LeServiceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LeServiceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LeServiceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LeService(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new LeService();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
