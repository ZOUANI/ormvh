import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OrmvahTestModule } from '../../../test.module';
import { SubdivisionComponent } from 'app/entities/subdivision/subdivision.component';
import { SubdivisionService } from 'app/entities/subdivision/subdivision.service';
import { Subdivision } from 'app/shared/model/subdivision.model';

describe('Component Tests', () => {
  describe('Subdivision Management Component', () => {
    let comp: SubdivisionComponent;
    let fixture: ComponentFixture<SubdivisionComponent>;
    let service: SubdivisionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [SubdivisionComponent],
      })
        .overrideTemplate(SubdivisionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubdivisionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubdivisionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Subdivision(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.subdivisions && comp.subdivisions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
